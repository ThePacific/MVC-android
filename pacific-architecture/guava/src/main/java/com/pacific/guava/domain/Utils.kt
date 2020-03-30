package com.pacific.guava.domain

fun isLogin1(): Boolean = Values.token1.isNotEmpty() && Values.token1.length > 8

fun isLogin2(): Boolean = Values.token2.isNotEmpty() && Values.token1.length > 8

fun isLogin3(): Boolean = Values.token3.isNotEmpty() && Values.token1.length > 8

@JvmOverloads
fun Throwable.toSourceException(code: Int = -1): SourceException {
    return if (this is SourceException) {
        this
    } else {
        SourceException(this.message, this.cause, code)
    }
}

fun Throwable.is404(): Boolean = this == sourceException404

fun Throwable.is403(): Boolean = this == sourceException403

val sourceException404: SourceException by lazy {
    SourceException("404", 404)
}

val sourceException403: SourceException by lazy {
    SourceException("403", 403)
}