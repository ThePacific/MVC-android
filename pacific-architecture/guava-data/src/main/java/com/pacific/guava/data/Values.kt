package com.pacific.guava.data

import androidx.collection.ArrayMap

/**
 * 内存缓存
 */
object Values {

    private val cache: ArrayMap<String, Any> = ArrayMap()

    fun put(key: String, obj: Any) {
        cache[key] = obj
    }

    /**
     * 获取数据
     * @param key 键值
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T? {
        val v = cache[key]
        return if (v == null) null else (v as T)
    }

    /**
     * 获取数据，并从内存中删除
     * @param key 键值
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> take(key: String): T? {
        val v = cache.remove(key)
        return if (v == null) null else (v as T)
    }

    /**
     * 清理所有数据
     */
    fun clear() = cache.clear()

    /**
     * 移除数据
     * @param keys 键值
     */
    fun remove(vararg keys: String) {
        keys.forEach {
            cache.remove(it)
        }
    }
}