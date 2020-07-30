package com.pacific.core.initializer

import com.pacific.guava.domain.JdkTimber
import timber.log.Timber

object AndroidTimber : JdkTimber {

    override fun d(tag: String, message: String) {
        Timber.tag(tag).d(message)
    }

    override fun d(e: Throwable) {
        Timber.d(e)
    }

    override fun e(e: Throwable) {
        Timber.e(e)
    }

    override fun e(tag: String, message: String) {
        Timber.tag(tag).e(message)
    }
}