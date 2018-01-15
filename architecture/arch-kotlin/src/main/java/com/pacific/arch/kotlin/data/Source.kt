package com.pacific.arch.kotlin.data

data class Source<out T> private constructor(val status: Status,
                                             val error: Throwable?,
                                             val data: T?) {
    companion object {
        @JvmStatic
        fun <T> inProgress(): Source<T> {
            return Source<T>(Status.IN_PROGRESS, null, null)
        }

        @JvmStatic
        fun <T> success(data: T): Source<T> {
            return Source(Status.SUCCESS, null, data)
        }

        @JvmStatic
        fun <T> failure(error: Throwable): Source<T> {
            return Source<T>(Status.ERROR, error, null)
        }

        @JvmStatic
        fun <T> irrelevant(): Source<T> {
            return Source<T>(Status.IRRELEVANT, null, null)
        }
    }
}