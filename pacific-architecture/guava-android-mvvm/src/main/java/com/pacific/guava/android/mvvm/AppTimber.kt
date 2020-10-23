package com.pacific.guava.android.mvvm

import com.pacific.guava.jvm.domain.PlatformTimber
import timber.log.Timber

class AppTimber : PlatformTimber {

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