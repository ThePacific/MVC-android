package com.pacific.guava.data

import com.pacific.guava.jvm.Guava
import com.pacific.guava.jvm.domain.Source
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

/**
 * 强制获取http返回体
 */
fun <T> Response<T>.bodyOrThrowException(): T {
    if (!isSuccessful) {
        throw HttpException(this)
    }
    return body()!!
}

/**
 * 二级封装网络错误
 */
fun <T> Response<T>.toException(): HttpException = HttpException(this)

/**
 * 自动重试
 */
suspend fun <T> Call<T>.executeWithRetry(
    defaultDelay: Long = 1000,
    maxAttempts: Int = 3,
    shouldRetry: (Exception) -> Boolean = ::defaultShouldRetry
): Response<T> {
    repeat(maxAttempts) { attempt ->
        var nextDelay = attempt * attempt * defaultDelay

        try {
            // Clone a new ready call if needed
            val call = if (isExecuted) clone() else this
            return call.execute()
        } catch (e: Exception) {
            // The response failed, so lets see if we should retry again
            if (attempt == (maxAttempts - 1) || !shouldRetry(e)) {
                throw e
            }

            if (e is HttpException) {
                // If we have a HttpException, check whether we have a Retry-After
                // header to decide how long to delay
                val retryAfterHeader = e.response()?.headers()?.get("Retry-After")
                if (retryAfterHeader != null && retryAfterHeader.isNotEmpty()) {
                    // Got a Retry-After value, try and parse it to an long
                    try {
                        nextDelay = (retryAfterHeader.toLong() + 10).coerceAtLeast(defaultDelay)
                    } catch (nfe: NumberFormatException) {
                        // Probably won't happen, ignore the value and use the generated default above
                    }
                }
            }
        }

        runBlocking {
            delay(nextDelay)
        }
    }

    // We should never hit here
    throw IllegalStateException("Unknown exception from executeWithRetry")
}

/**
 * 强制获取http返回体
 */
suspend fun <T> Call<T>.fetchBodyWithRetry(
    firstDelay: Long = 100,
    maxAttempts: Int = 3,
    shouldRetry: (Exception) -> Boolean = ::defaultShouldRetry
): T {
    return executeWithRetry(firstDelay, maxAttempts, shouldRetry).bodyOrThrowException()
}

/**
 * 判定是否需要重试
 */
fun defaultShouldRetry(exception: Exception) = when (exception) {
    is HttpException -> exception.code() == 429
    is IOException -> true
    else -> false
}

/**
 * 是否来自网络
 */
fun <T> Response<T>.isFromNetwork(): Boolean {
    return raw().cacheResponse == null
}

/**
 * 是否来缓存
 */
fun <T> Response<T>.isFromCache(): Boolean {
    return raw().cacheResponse != null
}

/**
 * 转化为业务Source结构
 */
suspend fun <T> Call<T>.toSource(): Source<T> {
    return try {
        execute().toSource()
    } catch (e: Exception) {
        Guava.timber.d(e)
        Source.Error(e)
    }
}

/**
 * 转化为业务Source结构
 */
suspend fun <T, E> Call<T>.toSource(mapper: suspend (T) -> E): Source<E> {
    return try {
        execute().toSource(mapper)
    } catch (e: Exception) {
        Guava.timber.d(e)
        Source.Error(e)
    }
}

/**
 * 转化为业务Source结构
 */
suspend fun <T> Response<T>.toSource(): Source<T> = toSource { it }

/**
 * 转化为业务Source结构
 */
suspend fun <T, E> Response<T>.toSource(mapper: suspend (T) -> E): Source<E> {
    delay(0)
    return if (isSuccessful) {
        Source.Success(mapper(bodyOrThrowException()))
    } else {
        Source.Error(toException())
    }
}

/**
 * 转化为业务Source结构
 */
suspend fun <T> Call<T>.sourceWith(obj: T): Source<T> {
    return try {
        execute().sourceWith(obj)
    } catch (e: Exception) {
        Guava.timber.d(e)
        Source.Error(e)
    }
}

/**
 * 转化为业务Source结构
 */
suspend fun <T, E> Response<T>.sourceWith(obj: E): Source<E> {
    delay(0)
    return if (isSuccessful) {
        Source.Success(obj)
    } else {
        Source.Error(toException())
    }
}