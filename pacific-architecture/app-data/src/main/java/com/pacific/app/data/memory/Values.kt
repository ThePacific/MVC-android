package com.pacific.app.data.memory

import androidx.collection.ArrayMap

object Values {

    private val cache: ArrayMap<String, Any> = ArrayMap()

    fun put(key: String, obj: Any) {
        cache[key] = obj
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(key: String): T {
        val value = cache[key]!!
        return value as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> take(key: String): T {
        val value = cache.remove(key)!!
        return value as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getNullable(key: String): T {
        val value = cache[key]
        return value as T
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> takeNullable(key: String): T {
        val value = cache.remove(key)
        return value as T
    }

    fun clear() = cache.clear()

    fun remove(vararg keys: String) {
        keys.forEach {
            cache.remove(it)
        }
    }
}