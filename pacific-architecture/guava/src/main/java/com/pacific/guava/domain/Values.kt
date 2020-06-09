package com.pacific.guava.domain

import androidx.collection.ArrayMap
import com.pacific.guava.GOOGLE

object Values {

    private val cache: ArrayMap<String, Any> = ArrayMap()

    var isDebug = false
    var apiUrl1 = GOOGLE
    var apiUrl2 = GOOGLE
    var apiUrl3 = GOOGLE
    var token1 = ""
    var token2 = ""
    var token3 = ""
    var userId = 0L
    var appProcessName = ""
    var loginName = ""
    var loginPassword = ""
    var deviceId = ""

    fun put(key: String, obj: Any) {
        cache[key] = obj
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> get(key: String): T? = cache[key] as T

    @Suppress("UNCHECKED_CAST")
    fun <T> take(key: String): T? = cache.remove(key) as T

    fun clear() = cache.clear()

    fun remove(vararg keys: String) {
        keys.forEach {
            cache.remove(it)
        }
    }
}