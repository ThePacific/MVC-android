package com.pacific.arch.data

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okio.ByteString
import java.io.IOException
import java.lang.reflect.Type

fun cancelOkhttp3Request(okHttpClient: OkHttpClient, tag: Any) {
    okHttpClient.dispatcher().queuedCalls()
            .filter { tag == it.request().tag() }
            .forEach { it.cancel() }
    okHttpClient.dispatcher().runningCalls()
            .filter { tag == it.request().tag() }
            .forEach { it.cancel() }
}

@JvmOverloads
@Suppress("UNCHECKED_CAST")
fun toMap(obj: Any, moshi: Moshi? = null): Map<String, String> {
    val mMoshi = moshi ?: Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val adapter = mMoshi!!.adapter(Any::class.java)
    return adapter.toJsonValue(obj) as Map<String, String>
}

@JvmOverloads
fun toJson(obj: Any, type: Type, moshi: Moshi? = null): String {
    val mMoshi = moshi ?: Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    return mMoshi.adapter<Any>(type).lenient().toJson(obj)
}

@JvmOverloads
@Throws(IOException::class)
@Suppress("UNCHECKED_CAST")
fun <T> fromJson(json: String, type: Type, moshi: Moshi? = null): T {
    val mMoshi = moshi ?: Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    return mMoshi!!.adapter<T>(type).lenient().fromJson(json)!!
}

@JvmOverloads
fun toByteArrayJson(obj: Any, type: Type, moshi: Moshi? = null) = str2ByteArray(toJson(obj, type, moshi))

@JvmOverloads
@Throws(IOException::class)
fun <T> fromByteArrayJson(byteArray: ByteArray, type: Type, moshi: Moshi? = null): T {
    return fromJson(byteArray2Str(byteArray), type, moshi)
}

fun str2ByteArray(src: String) = ByteString.encodeUtf8(src).toByteArray()!!

fun byteArray2Str(bytes: ByteArray) = ByteString.of(bytes, 0, bytes.size).utf8()!!

fun md5(src: String) = ByteString.encodeUtf8(src).md5().hex()!!

