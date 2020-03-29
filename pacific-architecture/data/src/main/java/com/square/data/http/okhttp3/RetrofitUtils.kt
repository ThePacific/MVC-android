package com.square.data.http.okhttp3

import com.square.guava.domain.Source
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun <T> Response<T>.bodyOrThrowException(): T {
    if (!isSuccessful) throw HttpException(this)
    return body()!!
}

fun <T> Response<T>.toException() = HttpException(this)

suspend inline fun <T> Call<T>.executeWithRetry(
        defaultDelay: Long = 100,
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

        delay(nextDelay)
    }

    // We should never hit here
    throw IllegalStateException("Unknown exception from executeWithRetry")
}

suspend inline fun <T> Call<T>.fetchBodyWithRetry(
        firstDelay: Long = 100,
        maxAttempts: Int = 3,
        shouldRetry: (Exception) -> Boolean = ::defaultShouldRetry
): T {
    return executeWithRetry(firstDelay, maxAttempts, shouldRetry).bodyOrThrowException()
}

fun defaultShouldRetry(exception: Exception) = when (exception) {
    is HttpException -> exception.code() == 429
    is IOException -> true
    else -> false
}

fun <T> Response<T>.isFromNetwork(): Boolean {
    return raw().cacheResponse == null
}

fun <T> Response<T>.isFromCache(): Boolean {
    return raw().cacheResponse != null
}

suspend fun <T> Response<T>.toSourceUnit(): Source<Unit> = toSource { Unit }

suspend fun <T> Response<T>.toSource(): Source<T> = toSource { it }

suspend fun <T, E> Response<T>.toSource(mapper: suspend (T) -> E): Source<E> {
    return try {
        if (isSuccessful) {
            Source.Success(mapper(bodyOrThrowException()))
        } else {
            Source.Error(toException())
        }
    } catch (e: Exception) {
        Source.Error(e)
    }
}