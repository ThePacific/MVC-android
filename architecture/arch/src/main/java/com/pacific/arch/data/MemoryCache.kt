package com.pacific.arch.data

import android.support.v4.util.LruCache
import java.util.*
import javax.inject.Inject

class MemoryCache @Inject constructor() {
    private val cache = LruCache<String, MemoryCacheEntry>(Int.MAX_VALUE)
    private val keys = LinkedList<String>()

    fun get(key: String, evictExpired: Boolean): MemoryCacheEntry? {
        var value: MemoryCacheEntry = cache.get(key) ?: return null
        if (evictExpired && value.isExpired()) {
            remove(key)
            return null
        }
        return value
    }

    fun put(key: String, value: MemoryCacheEntry): MemoryCacheEntry {
        if (!keys.contains(key)) {
            keys.add(key)
        }
        return cache.put(key, value)
    }

    fun remove(key: String): MemoryCacheEntry {
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

    fun snapshot() = cache.snapshot()

    fun trimToSize(maxSize: Int) = cache.trimToSize(maxSize)

    fun createCount() = cache.createCount()

    fun evictAll() = cache.evictAll()

    fun evictionCount() = cache.evictionCount()

    fun hitCount() = cache.hitCount()

    fun maxSize() = cache.maxSize()

    fun missCount() = cache.missCount()

    fun putCount() = cache.putCount()

    fun size() = cache.size()
}