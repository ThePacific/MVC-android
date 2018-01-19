package com.pacific.arch.data

data class Source<out T> private constructor(@JvmField val status: Status,
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