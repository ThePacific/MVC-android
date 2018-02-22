package com.pacific.example.data

import com.pacific.arch.data.DiskCache
import com.pacific.arch.data.Envelope
import com.pacific.arch.data.MemoryCache
import com.pacific.arch.data.Store
import com.squareup.moshi.Moshi
import io.reactivex.Observable
import java.lang.reflect.Type

abstract class BaseStore<K, V> constructor(moshi: Moshi, diskCache: DiskCache, memoryCache: MemoryCache)
    : Store<K, V>(moshi, diskCache, memoryCache) {

    override fun dispatchNetwork(query: K): Observable<out Envelope<V>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isIrrelevant(data: V): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dataType(): Type {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}