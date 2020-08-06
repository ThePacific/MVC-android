package com.pacific.guava.jvm.domain

sealed class Source<out T> {

    data class Success<T>(val data: T) : Source<T>()

    data class Error<T>(val throwable: Throwable) : Source<T>()

    /**
     * Returns the available data or throws [NullPointerException] if there is no data.
     */
    fun requireData(): T {
        return when (this) {
            is Success -> data
            is Error -> throw throwable
        }
    }

    fun requireError(): Throwable {
        return when (this) {
            is Success -> throw IllegalStateException("no throwable for Success.Data")
            is Error -> throwable
        }
    }

    /**
     * If this [Source] is of type [Source.Error], throws the exception
     * Otherwise, does nothing.
     */
    fun throwIfError() {
        if (this is Error) {
            throw throwable
        }
    }

    /**
     * If this [Source] is of type [Source.Error], returns the available error
     * from it. Otherwise, returns `null`.
     */
    fun errorOrNull(): Throwable? = when (this) {
        is Error -> throwable
        else -> null
    }

    /**
     * If there is data available, returns it; otherwise returns null.
     */
    fun dataOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun <R> swapType(): Source<R> = when (this) {
        is Error -> Error(
            throwable
        )
        is Success -> throw IllegalStateException("cannot swap type for Success.Data")
    }
}