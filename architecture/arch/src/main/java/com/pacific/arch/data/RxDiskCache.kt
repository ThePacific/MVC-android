package com.pacific.arch.data

import com.squareup.moshi.Json

class RxDiskCache {
    class Entry(@Json(name = "data") val data: ByteArray,
                @Json(name = "TTL") val TTL: Long,
                @Json(name = "softTTL") val softTTL: Long) {

        fun isExpired() = this.TTL < System.currentTimeMillis()

        override fun toString() = toJson(this, DiskCache.Entry::class.java)

        fun refreshNeeded() = this.softTTL < System.currentTimeMillis()
    }
}