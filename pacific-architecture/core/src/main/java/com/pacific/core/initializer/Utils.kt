package com.pacific.core.initializer

import android.app.Application
import android.os.StrictMode
import com.pacific.guava.Guava
import com.pacific.guava.domain.JdkTimber
import com.tencent.mmkv.MMKV
import timber.log.Timber

internal fun enableStrictMode() {
    StrictMode.setThreadPolicy(
        StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build()
    )
    StrictMode.setVmPolicy(
        StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build()
    )
}

internal fun loadValues(app: Application, isDebug: Boolean) {
    MMKV.initialize(app)
    Guava.isDebug = isDebug
    Guava.timber = object : JdkTimber {

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
}