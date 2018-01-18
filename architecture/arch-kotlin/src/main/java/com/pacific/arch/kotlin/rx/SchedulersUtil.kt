package com.pacific.arch.kotlin.rx

import android.os.Looper
import com.pacific.arch.kotlin.BuildConfig
import io.reactivex.android.MainThreadDisposable
import io.reactivex.android.schedulers.AndroidSchedulers

fun isMainThread(): Boolean {
    return try {
        Looper.myLooper() == Looper.getMainLooper()
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