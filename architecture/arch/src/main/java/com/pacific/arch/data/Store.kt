package com.pacific.arch.data

import android.support.annotation.WorkerThread
import com.pacific.arch.rx.verifyWorkThread
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

abstract class Store<in K, V>(@JvmField protected val moshi: Moshi,
                              @JvmField protected val diskCache: DiskCache,
                              @JvmField protected val memoryCache: MemoryCache,
                              @JvmField protected var key: String = "") {

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

    /**
     * @return default network cache time is 10 MINUTES
     */
    protected open fun ttl(): Int {
        return 10
    }

    /**
     * @return default refresh cache time
     */
    protected open fun softTTL(): Int {
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
    protected open fun getKey(query: K): String {
        return md5(dataType().toString())
    }

    /**
     * @return to make sure never returning empty or null data
     */
    protected abstract fun isIrrelevant(data: V): Boolean

    /**
     * @return request HTTP/HTTPS API
     */
    protected abstract fun dispatchNetwork(query: K): Observable<out Envelope<V>>

    /**
     * User `Types.newParameterizedType(...)`
     */
    protected abstract fun dataType(): Type
}