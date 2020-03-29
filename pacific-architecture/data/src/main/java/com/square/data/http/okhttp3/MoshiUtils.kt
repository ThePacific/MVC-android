package com.square.data.http.okhttp3

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

inline fun <reified T> JsonAdapter<T>.gentle(): JsonAdapter<T> {
    return this.lenient().serializeNulls().nullSafe()
}

@Suppress("UNCHECKED_CAST")
fun Moshi.retrofitMap(obj: Any): Map<String, String> {
    return (this.adapter(Any::class.java).gentle().toJsonValue(obj)) as Map<String, String>
}
