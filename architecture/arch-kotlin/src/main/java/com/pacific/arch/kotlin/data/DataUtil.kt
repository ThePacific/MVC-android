package com.pacific.arch.kotlin.data

import android.os.Looper
import com.pacific.arch.kotlin.BuildConfig
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.reactivex.android.MainThreadDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
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

fun md5(src: String) = ByteString.encodeUtf8(src).md5().hex()!!

fun toMap(obj: Any, moshi: Moshi?): Map<String, String> {
    var localMoshi = moshi
    if (localMoshi == null) {
        localMoshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    val adapter = localMoshi!!.adapter(Any::class.java)
    return adapter.toJsonValue(obj) as Map<String, String>
}

fun toJson(obj: Any, moshi: Moshi?, type: Type): String {
    var localMoshi = moshi
    if (localMoshi == null) {
        localMoshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    return localMoshi!!.adapter<Any>(type).lenient().toJson(obj)
}

@Throws(IOException::class)
fun <T> fromJson(json: String, moshi: Moshi?, type: Type): T {
    var localMoshi = moshi
    if (localMoshi == null) {
        localMoshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }
    return localMoshi!!.adapter<Any>(type).lenient().fromJson(json) as T
}

fun string2ByteArray(src: String): ByteArray {
    return ByteString.encodeUtf8(src).toByteArray()
}

fun byteArray2String(bytes: ByteArray): String {
    return ByteString.of(bytes, 0, bytes.size).utf8()
}

fun toJsonByteArray(obj: Any, moshi: Moshi?, type: Type): ByteArray {
    return string2ByteArray(toJson(obj, moshi, type))
}

@Throws(IOException::class)
fun <T> fromJsonByteArray(byteArray: ByteArray, moshi: Moshi?, type: Type): T {
    return fromJson(byteArray2String(byteArray), moshi, type)
}

fun isMainThread(): Boolean {
    return try {
        Looper.myLooper() == Looper.getMainLooper()
    } catch (e: Exception) {
        true// Cover for tests
    }
}

fun verifyMainThread() {
    if (BuildConfig.DEBUG) {
        return // Cover for tests
    }
    MainThreadDisposable.verifyMainThread()
}

fun verifyWorkThread() {
    if (BuildConfig.DEBUG) {
        return // Cover for tests
    }
    if (isMainThread()) {
        throw UnsupportedOperationException("Can't run in Main thread")
    }
}

fun postToMainThread(runnable: Runnable) {
    AndroidSchedulers.mainThread().scheduleDirect(runnable)
}