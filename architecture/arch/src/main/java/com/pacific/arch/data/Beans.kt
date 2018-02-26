package com.pacific.arch.data

import android.annotation.TargetApi
import com.squareup.moshi.Json
import java.lang.RuntimeException

interface Envelope<out T> {
    fun status(): Boolean
    fun code(): Int
    fun message(): String
    fun data(): T?
}

class DiskCacheEntry(@JvmField @Json(name = "data") val data: ByteArray,
                     @JvmField @Json(name = "TTL") val TTL: Long,
                     @JvmField @Json(name = "softTTL") val softTTL: Long) {
    fun isExpired() = this.TTL < System.currentTimeMillis()

    fun refreshNeeded() = softTTL < System.currentTimeMillis()

    override fun toString() = toJson(this, DiskCacheEntry::class.java)
}

class MemoryCacheEntry(@JvmField @Json(name = "data") val data: Any?,
                       @JvmField @Json(name = "TTL") val TTL: Long) {
    fun isExpired() = TTL < System.currentTimeMillis()
}

class Source<out T> private constructor(@JvmField val status: Status,
                                        @JvmField val error: Throwable?,
                                        @JvmField val data: T?) {
    companion object {
        fun <T> inProgress(): Source<T> {
            return Source<T>(Status.IN_PROGRESS, null, null)
        }

        fun <T> success(data: T): Source<T> {
            return Source(Status.SUCCESS, null, data)
        }

        fun <T> failure(error: Throwable): Source<T> {
            return Source<T>(Status.ERROR, error, null)
        }

        fun <T> irrelevant(): Source<T> {
            return Source<T>(Status.IRRELEVANT, null, null)
        }
    }
}

enum class Status {
    SUCCESS, ERROR, IN_PROGRESS, IRRELEVANT
}

class FlowException : RuntimeException {
    @JvmField
    val errorCode: Int

    constructor(code: Int) {
        this.errorCode = code
    }

    constructor(message: String, code: Int) : super(message) {
        this.errorCode = code
    }

    constructor(message: String, cause: Throwable, code: Int) : super(message, cause) {
        this.errorCode = code
    }

    constructor(cause: Throwable, code: Int) : super(cause) {
        this.errorCode = code
    }

    @TargetApi(24)
    constructor(message: String, cause: Throwable, enableSuppression: Boolean,
                writableStackTrace: Boolean, code: Int
    ) : super(message, cause, enableSuppression, writableStackTrace) {
        this.errorCode = code
    }

    companion object {
        fun isFlowException(e: Throwable): Boolean {
            return e is FlowException
        }

        fun from(e: Throwable): FlowException {
            return e as? FlowException ?: FlowException(e.message!!, e.cause!!, -1)
        }
    }
}