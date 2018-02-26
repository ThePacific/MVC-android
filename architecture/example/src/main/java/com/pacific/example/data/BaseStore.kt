package com.pacific.example.data

import com.pacific.arch.data.*
import com.squareup.moshi.Moshi

abstract class BaseStore<in K, V> constructor(@JvmField protected val dataService: DataService,
                                              moshi: Moshi,
                                              diskCache: DiskCache,
                                              memoryCache: MemoryCache)
    : Store<K, V>(moshi, diskCache, memoryCache) {

    override fun isEvictAll(flowException: FlowException): Boolean {
        return super.isEvictAll(flowException)
    }

    fun httpParameters(query: K): Map<String, String> {
        return toMap(query as Any, moshi)
    }
}