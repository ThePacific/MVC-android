package com.square.data.repository.cache

import com.squareup.moshi.Json

class MemoryCacheEntry(
    @JvmField @Json(name = "data") val data: Any?,
    @JvmField @Json(name = "ttl") val ttl: Long
) {
    fun isExpired() = ttl < System.currentTimeMillis()
}