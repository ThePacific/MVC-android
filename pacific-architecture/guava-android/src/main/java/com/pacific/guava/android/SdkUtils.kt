package com.pacific.guava.android

import android.annotation.SuppressLint
import android.os.Build
import android.os.Looper
import androidx.arch.core.executor.ArchTaskExecutor
import io.reactivex.rxjava3.android.MainThreadDisposable

fun isMainThread() = Looper.getMainLooper().thread == Thread.currentThread()

fun ensureMainThread() = MainThreadDisposable.verifyMainThread()

fun ensureWorkThread() {
    check(!isMainThread()) { "Expected to be called on work thread" }
}

@SuppressLint("RestrictedApi")
fun postToMainThread(runnable: Runnable) {
    ArchTaskExecutor.getInstance().postToMainThread(runnable)
}

@SuppressLint("RestrictedApi")
fun executeOnDiskIO(runnable: Runnable) {
    ArchTaskExecutor.getInstance().executeOnDiskIO(runnable)
}

fun verifySdk(version: Int): Boolean = Build.VERSION.SDK_INT >= version