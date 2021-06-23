package com.pacific.guava.data

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

/**
 * 包容模式，默认是严格模式
 */
inline fun <reified T> JsonAdapter<T>.gentle(): JsonAdapter<T> {
    return this.lenient().serializeNulls().nullSafe()
}

/**
 * obj->map，包含null字段
 */
@Suppress("UNCHECKED_CAST")
fun Moshi.retrofitMap(any: Any): Map<String, String> {
    return (this.adapter(Any::class.java).gentle().toJsonValue(any)) as Map<String, String>
}

/**
 * obj->map，排除null字段
 */
@Suppress("UNCHECKED_CAST")
fun Moshi.retrofitTrimMap(any: Any): Map<String, String> {
    return (this.adapter(Any::class.java).lenient().toJsonValue(any)) as Map<String, String>
}
