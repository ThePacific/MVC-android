package com.pacific.arch.data

import android.util.LruCache
import com.squareup.moshi.Json
import java.util.*
import javax.inject.Inject

class RxMemoryCache @Inject constructor() : LruCache<String, RxMemoryCache.Entry>(Int.MAX_VALUE) {
    private val keys: LinkedList<String> = LinkedList()

    fun get(key: String, evictExpired: Boolean): Entry? {
        var value: RxMemoryCache.Entry? = get(key)
        if (evictExpired) {
            if (value != null && value.isExpired()) {
                doRemove(key)
                value = null
            }
        }
        return value
    }

    fun doPut(key: String, value: Entry): Entry {
        if (!keys.contains(key)) {
            keys.add(key)
        }
        return put(key, value)

    }

    fun doRemove(key: String): Entry {
        keys.remove(key)
        return remove(key)
    }

    class Entry(@Json(name = "data") val data: Any?,
                @Json(name = "TTL") private val TTL: Long) {

        fun isExpired() = this.TTL < System.currentTimeMillis()
    }
}
