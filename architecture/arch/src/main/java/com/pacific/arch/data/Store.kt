package com.pacific.arch.data

import android.support.annotation.WorkerThread
import com.pacific.arch.gauva.Preconditions2
import com.pacific.arch.rx.verifyWorkThread
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

abstract class Store<in K, V>(@JvmField protected val moshi: Moshi,
                              @JvmField protected val diskCache: DiskCache,
                              @JvmField protected val memoryCache: MemoryCache) {
    @JvmField
    protected var key: String = ""

    @WorkerThread
    fun get(query: K): Observable<Source<V>> {
        verifyWorkThread()
        return stream(query, true)
                .flatMap {
                    if (it.status == Status.SUCCESS) {
                        return@flatMap Observable.just(it)
                    }
                    return@flatMap load(query)
                }
                .flatMap {
                    if (it.status == Status.SUCCESS) {
                        return@flatMap Observable.just(it)
                    }
                    return@flatMap fetch(query, true, true)
                }
    }

    @WorkerThread
    fun fetch(query: K, toDisk: Boolean, toMemory: Boolean): Observable<Source<V>> {
        verifyWorkThread()
        key = getKey(query)
        return dispatchNetwork(query).flatMap {
            if (it.status()) {
                return@flatMap onSave(it, toDisk, toMemory, true, true)
            }
            return@flatMap onError(it, false, false)
        }
    }

    @WorkerThread
    @Suppress("UNCHECKED_CAST")
    fun load(query: K): Observable<Source<V>> {
        verifyWorkThread()
        key = getKey(query)
        return Observable.defer {
            val entry = diskCache.get(key) ?: return@defer Observable.just(Source.irrelevant<V>())
            val json = byteArray2Str(entry.data)
            val newData: V = fromJson(json, dataType(), moshi)
            if (entry.isExpired() || isIrrelevant(newData)) {
                evictMemoryCache()
                evictDiskCache()
                return@defer Observable.just(Source.irrelevant<V>())
            }
            memoryCache.put(key, MemoryCacheEntry(newData as Any, entry.TTL))
            return@defer Observable.just(Source.success<V>(newData))
        }
    }

    @JvmOverloads
    @Suppress("UNCHECKED_CAST")
    fun stream(query: K? = null, evictExpired: Boolean = true): Observable<Source<V>> {
        if (query != null) {
            key = getKey(query)
        }
        return Observable.defer {
            val entry = memoryCache.get(key, evictExpired)
                    ?: return@defer Observable.just(Source.irrelevant<V>())
            val newData = entry.data as V
            if (isIrrelevant(newData)) {
                return@defer Observable.just(Source.irrelevant<V>())
            }
            return@defer Observable.just(Source.success(newData))
        }
    }

    @JvmOverloads
    @Suppress("UNCHECKED_CAST")
    @Throws(IllegalStateException::class)
    fun memory(evictExpired: Boolean = false): V {
        val entry = memoryCache.get(key, evictExpired) ?: throw IllegalStateException()
        val data = entry.data as V
        if (isIrrelevant(data)) throw IllegalStateException()
        return data
    }

    fun evictMemoryCache() {
        memoryCache.remove(key)
    }

    @WorkerThread
    fun evictDiskCache() {
        verifyWorkThread()
        try {
            diskCache.remove(key)
        } catch (ignored: IOException) {
            ignored.printStackTrace()
        }
    }

    @WorkerThread
    fun evictAll() {
        verifyWorkThread()
        try {
            diskCache.evictAll()
            memoryCache.evictAll()
        } catch (ignored: IOException) {
            ignored.printStackTrace()
        }
    }

    protected open fun onError(envelope: Envelope<V>,
                               evictDiskCache: Boolean,
                               evictMemoryCache: Boolean): Observable<Source<V>> {
        val flowException = FlowException(envelope.message(), envelope.code())
        if (isEvictAll(flowException)) {
            evictAll()
        } else {
            if (evictDiskCache) {
                evictDiskCache()
            }
            if (evictMemoryCache) {
                evictMemoryCache()
            }
        }
        return Observable.just(Source.failure(flowException))
    }

    protected open fun onSave(envelope: Envelope<V>,
                              toDisk: Boolean,
                              toMemory: Boolean,
                              evictDiskCacheIfIrrelevant: Boolean,
                              evictMemoryCacheIfIrrelevant: Boolean): Observable<Source<V>> {
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
        val softTTL = now + timeUnit.toMillis(softTTL().toLong())

        Preconditions2.checkState(ttl > now && softTTL > now && ttl >= softTTL)

        if (toDisk) {
            val bytes = toByteArrayJson(newData as Any, dataType(), moshi)
            diskCache.put(key, DiskCacheEntry(bytes, ttl, softTTL))
        } else {
            evictDiskCache()
        }
        if (toMemory) {
            memoryCache.put(key, MemoryCacheEntry(newData as Any, ttl))
        } else {
            evictMemoryCache()
        }
        return Observable.just(Source.success(newData!!))
    }

    /**
     * @return default network cache time is 10 MINUTES
     */
    protected open fun ttl() = 10

    /**
     * @return default refresh cache time
     */
    protected open fun softTTL() = ttl()

    /**
     * Default TimeUnit is `TimeUnit.MINUTES`
     */
    protected open fun timeUnit() = TimeUnit.MINUTES

    /**
     * @return cache key
     */
    protected open fun getKey(query: K): String = md5(dataType().toString())

    /**
     * @return true to evict the global cache including DiskCache and MemoryCache
     */
    protected open fun isEvictAll(flowException: FlowException) = false

    /**
     * @return request HTTP/HTTPS API
     */
    protected abstract fun dispatchNetwork(query: K): Observable<out Envelope<V>>

    /**
     * @return to make sure never save/return empty or null data
     */
    protected abstract fun isIrrelevant(data: V?): Boolean

    /**
     * User `Types.newParameterizedType(...)`
     */
    protected abstract fun dataType(): Type
}