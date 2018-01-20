package com.pacific.arch.rx

import android.annotation.SuppressLint
import com.pacific.arch.BuildConfig
import com.uber.autodispose.android.internal.AutoDisposeAndroidUtil
import io.reactivex.android.MainThreadDisposable
import io.reactivex.android.schedulers.AndroidSchedulers

@SuppressLint("RestrictedApi")
fun isMainThread(): Boolean {
    return try {
        return AutoDisposeAndroidUtil.isMainThread()
    } catch (e: Exception) {
        true// Cover for tests
    }
}

fun verifyMainThread() {
    if (BuildConfig.DEBUG) {
        return // Cover for tests
    }
    MainThreadDisposable.verifyMainThread()
}

fun verifyWorkThread() {
    if (BuildConfig.DEBUG) {
        return // Cover for tests
    }
    if (isMainThread()) {
        throw UnsupportedOperationException("Can't run in MainThread")
    }
}

fun postToMainThread(runnable: Runnable) {
    AndroidSchedulers.mainThread().scheduleDirect(runnable)
}