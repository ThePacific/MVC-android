package com.pacific.arch.data

import android.support.v4.util.LruCache
import com.squareup.moshi.Json
import java.util.*
import javax.inject.Inject

class MemoryCache(maxSize: Int) {
    private val cache: LruCache<String, Entry> = LruCache(maxSize)
    private val keys = LinkedList<String>()

    @Inject
    constructor() : this(Int.MAX_VALUE)

    fun get(key: String, evictExpired: Boolean): Entry? {
        var value: Entry? = cache.get(key)
        if (evictExpired) {
            if (value != null && value.isExpired()) {
                remove(key)
                value = null
            }
        }
        return value
    }

    @Synchronized
    fun put(key: String, value: Entry): Entry {
        if (!keys.contains(key)) {
            keys.add(key)
        }
        return cache.put(key, value)

    }

    fun remove(key: String): Entry {
        keys.remove(key)
        return cache.remove(key)
    }

    fun evictExpired() {
        var key: String
        var i = 0
        while (i < keys.size) {
            key = keys[i]
            val value = cache.get(key)
            if (value != null && value.isExpired()) {
                remove(key)
            } else {
                i++
            }
        }
    }

    fun snapshot(): Map<String, Entry> {
        return cache.snapshot()
    }

    fun trimToSize(maxSize: Int) {
        cache.trimToSize(maxSize)
    }

    fun createCount(): Int {
        return cache.createCount()
    }

    fun evictAll() {
        cache.evictAll()
    }

    fun evictionCount(): Int {
        return cache.evictionCount()
    }

    fun hitCount(): Int {
        return cache.hitCount()
    }

    fun maxSize(): Int {
        return cache.maxSize()
    }

    fun missCount(): Int {
        return cache.missCount()
    }

    fun putCount(): Int {
        return cache.putCount()
    }

    fun size(): Int {
        return cache.size()
    }

    class Entry(@JvmField @Json(name = "data") val data: Any?,
                @JvmField @Json(name = "TTL") val TTL: Long) {

        fun isExpired() = this.TTL < System.currentTimeMillis()
    }
}