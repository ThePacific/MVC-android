package com.square.data.repository.cache

import androidx.collection.LruCache
import java.util.*

open class MemoryCache<K> {

    private val cache = LruCache<K, MemoryCacheEntry>(Int.MAX_VALUE)
    private val keys = LinkedList<K>()

    fun get(key: K, evictExpired: Boolean): MemoryCacheEntry? {
        var value: MemoryCacheEntry = cache.get(key) ?: return null
        if (evictExpired && value.isExpired()) {
            remove(key)
            return null
        }
        return value
    }

    fun put(key: K, value: MemoryCacheEntry): MemoryCacheEntry? {
        if (!keys.contains(key)) {
            keys.add(key)
        }
        return cache.put(key, value)
    }

    fun remove(key: K): MemoryCacheEntry? {
        keys.remove(key)
        return cache.remove(key)
    }

    fun evictExpired() {
        var key: K
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

    fun snapshot(): Map<K, MemoryCacheEntry> = cache.snapshot()

    fun trimToSize(maxSize: Int) = cache.trimToSize(maxSize)

    fun createCount() = cache.createCount()

    fun evictAll() {
        cache.evictAll()
        keys.clear()
    }

    fun evictionCount() = cache.evictionCount()

    fun hitCount() = cache.hitCount()

    fun maxSize() = cache.maxSize()

    fun missCount() = cache.missCount()

    fun putCount() = cache.putCount()

    fun size() = cache.size()
}