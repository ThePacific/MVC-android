package com.pacific.example.data

import com.pacific.arch.data.DiskCache
import com.pacific.arch.data.FlowException
import com.pacific.arch.data.MemoryCache
import com.pacific.arch.data.Store
import com.squareup.moshi.Moshi

abstract class BaseStore<K, V> constructor(moshi: Moshi, diskCache: DiskCache, memoryCache: MemoryCache)
    : Store<K, V>(moshi, diskCache, memoryCache) {

    override fun isEvictAll(flowException: FlowException): Boolean {
        return super.isEvictAll(flowException)
    }
}