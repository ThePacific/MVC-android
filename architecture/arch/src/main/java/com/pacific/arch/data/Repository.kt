package com.pacific.arch.data

import android.support.annotation.WorkerThread
import android.support.v4.util.Preconditions2
import android.text.TextUtils
import com.pacific.arch.rx.verifyWorkThread
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

abstract class Repository<in T, R>(@JvmField protected val moshi: Moshi,
                                   @JvmField protected val diskCache: DiskCache,
                                   @JvmField protected val memoryCache: MemoryCache,
                                   @JvmField protected var key: String?) {

    fun get(query: T): Observable<Source<R>> {
        verifyWorkThread()
        return stream(query, true)
                .flatMap { it ->
                    if (it.status == Status.SUCCESS) {
                        return@flatMap Observable.just(it)
                    }
                    load(query)
                }
                .flatMap { it ->
                    if (it.status == Status.SUCCESS) {
                        return@flatMap Observable.just(it)
                    }
                    fetch(query, true, true)
                }
    }

    fun fetch(query: T, toDisk: Boolean, toMemory: Boolean): Observable<Source<R>> {
        verifyWorkThread()
        key = getKey(query)
        return dispatchNetwork(query).flatMap { it ->
            if (it.isSuccess) {
                return@flatMap onPersist(it, toDisk, toMemory, true, true)
            }
            onError(it, false, false)
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun load(query: T): Observable<Source<R>> {
        verifyWorkThread()
        key = getKey(query)
        return Observable.defer {
            val diskEntry = diskCache.get(key!!)
                    ?: return@defer Observable.just(Source.irrelevant<R>())
            val json = byteArray2Str(diskEntry.data)
            val newData: R = fromJson(json, dataType(), moshi)
            if (diskEntry.isExpired || isIrrelevant(newData)) {
                memoryCache.remove(key!!)
                evictDiskCache()
                return@defer Observable.just(Source.irrelevant<R>())
            }
            memoryCache.put(key!!, MemoryCache.Entry.create(newData as Any, diskEntry.TTL))
            Observable.just(Source.success<R>(newData))
        }
    }

    @JvmOverloads
    @Suppress("UNCHECKED_CAST")
    fun stream(query: T? = null, evictExpired: Boolean = true): Observable<Source<R>> {
        if (query != null) {
            key = getKey(query)
        }
        return Observable.defer {
            //No need to check isExpired(), MemoryCache.get() has already done
            val entry = memoryCache.get(key!!, evictExpired)
                    ?: return@defer Observable.just(Source.irrelevant<R>())
            val newData = entry.data as R
            if (isIrrelevant(newData)) {
                return@defer Observable.just(Source.irrelevant<R>())
            }
            return@defer Observable.just(Source.success(newData))
        }
    }

    @Throws(IllegalStateException::class)
    @JvmOverloads
    @Suppress("UNCHECKED_CAST")
    fun memory(evictExpired: Boolean = false): R {
        val entry = memoryCache.get(key!!, evictExpired)
                ?: throw IllegalStateException("Not supported")
        val newData = entry.data as R
        if (isIrrelevant(newData)) {
            throw IllegalStateException("Not supported")
        }
        return entry.data
    }

    @Throws(IOException::class)
    protected fun onError(envelope: Envelope<R>,
                          evictDiskCache: Boolean,
                          evictMemoryCache: Boolean): Observable<Source<R>> {
        val ioError = IOError(envelope.message(), envelope.code())
        if (isAccessFailure(ioError)) {
            diskCache.evictAll()
            memoryCache.evictAll()
        } else {
            if (evictDiskCache) {
                evictDiskCache()
            }
            if (evictMemoryCache) {
                evictMemoryCache()
            }
        }
        return Observable.just(Source.failure(ioError))
    }

    protected fun onPersist(envelope: Envelope<R>, toDisk: Boolean,
                            toMemory: Boolean,
                            evictDiskCacheIfIrrelevant: Boolean,
                            evictMemoryCacheIfIrrelevant: Boolean): Observable<Source<R>> {
        val newData = envelope.data()
        if (isIrrelevant(newData)) {
            if (evictDiskCacheIfIrrelevant) {
                evictDiskCache()
            }
            if (evictMemoryCacheIfIrrelevant) {
                evictMemoryCache()
            }
            return Observable.just(Source.irrelevant())
        }
        val timeUnit = timeUnit()
        val now = System.currentTimeMillis()
        val ttl = now + timeUnit.toMillis(ttl().toLong())
        val softTtl = now + timeUnit.toMillis(softTtl().toLong())
        Preconditions2.checkState(ttl > now && softTtl > now && ttl >= softTtl)
        if (toDisk) {
            val bytes = toByteArrayJson(newData as Any, dataType(), moshi)
            diskCache.put(key!!, DiskCache.Entry.create(bytes, ttl, softTtl))
        } else {
            evictDiskCache()
        }
        if (toMemory) {
            memoryCache.put(key!!, MemoryCache.Entry.create(newData as Any, ttl))
        } else {
            evictMemoryCache()
        }
        return Observable.just(Source.success(newData))
    }

    fun evictMemoryCache() {
        if (TextUtils.isEmpty(key)) {
            return
        }
        memoryCache.remove(key!!)
    }

    @WorkerThread
    fun evictDiskCache() {
        if (TextUtils.isEmpty(key)) {
            return
        }
        verifyWorkThread()
        try {
            diskCache.remove(key!!)
        } catch (ignored: IOException) {
            ignored.printStackTrace()
        }

    }

    /**
     * @return default network cache time is 10 MINUTES
     */
    protected open fun ttl(): Int {
        return 10
    }

    /**
     * @return default refresh cache time
     */
    protected open fun softTtl(): Int {
        return ttl()
    }

    /**
     * Default TimeUnit is `TimeUnit.MINUTES`
     */
    protected open fun timeUnit(): TimeUnit {
        return TimeUnit.MINUTES
    }

    /**
     * @return cache key
     */
    protected open fun getKey(query: T): String {
        return md5(dataType().toString())
    }

    /**
     * @return to make sure never returning empty or null data
     */
    protected abstract fun isIrrelevant(data: R): Boolean

    /**
     * @return request HTTP/HTTPS API
     */
    protected abstract fun dispatchNetwork(query: T): Observable<out Envelope<R>>

    /**
     * User `Types.newParameterizedType(...)`
     */
    protected abstract fun dataType(): Type

    protected abstract fun isAccessFailure(ioError: IOError): Boolean
}