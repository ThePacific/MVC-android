package com.pacific.guava.jvm.domain

class SourceException : RuntimeException {

    @get:JvmName("code")
    val code: Int

    constructor(code: Int) : super() {
        this.code = code
    }

    constructor(s: String, code: Int) : super(s) {
        this.code = code
    }

    constructor(s: String, throwable: Throwable, code: Int) : super(s, throwable) {
        this.code = code
    }

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }
}