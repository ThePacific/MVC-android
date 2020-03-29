package com.square.data.repository.cache

import com.squareup.moshi.Json

class DiskCacheEntry(
    @JvmField @Json(name = "data") val data: ByteArray,
    @JvmField @Json(name = "ttl") val ttl: Long,
    @JvmField @Json(name = "softTTL") val softTTL: Long
) {
    fun isExpired() = ttl < System.currentTimeMillis()

    fun refreshNeeded() = softTTL < System.currentTimeMillis()
}