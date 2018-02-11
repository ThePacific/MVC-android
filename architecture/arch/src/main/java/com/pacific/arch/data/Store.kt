package com.pacific.arch.data

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface Store<in K, V> {
    fun get(key: K): Maybe<out V>
    fun getAll(key: K): Maybe<List<V>>

    fun fetch(key: K)
    fun request(key: K): Single<V>

    fun delete(value: V): Completable
    fun deleteAll(values: Maybe<List<V>>): Completable

    fun push(value: V): Completable
    fun pushAll(values: Maybe<List<V>>): Completable

    fun clear()
}