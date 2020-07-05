package com.pacific.guava.domain

class SourceException : RuntimeException {

    @get:JvmName("code")
    val code: Int

    constructor(code: Int) : super() {
        this.code = code
    }

    constructor(message: String?, code: Int) : super(message) {
        this.code = code
    }

    constructor(throwable: Throwable?, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String?, throwable: Throwable? , code: Int) : super(message, throwable) {
        this.code = code
    }
}