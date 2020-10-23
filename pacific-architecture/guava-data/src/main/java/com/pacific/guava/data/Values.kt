package com.pacific.guava.data

import androidx.collection.ArrayMap

object Values {

    private val cache: ArrayMap<String, Any> = ArrayMap()

    fun put(key: String, obj: Any) {
        cache[key] = obj
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T = (cache[key] as T)!!

    @Suppress("UNCHECKED_CAST")
    fun <T> take(key: String): T = (cache.remove(key) as T)!!

    fun clear() = cache.clear()

    fun remove(vararg keys: String) {
        keys.forEach {
            cache.remove(it)
        }
    }
}