package com.pacific.guava.android

import android.os.Build
import android.os.Looper
import io.reactivex.rxjava3.android.MainThreadDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

fun isMainThread() = Looper.getMainLooper().thread == Thread.currentThread()

fun ensureMainThread() = MainThreadDisposable.verifyMainThread()

fun ensureWorkThread() {
    check(!isMainThread()) { "Expected to be called on work thread" }
}

fun postToMainThread(runnable: Runnable) = GlobalScope.launch(Dispatchers.Main) {
    runnable.run()
}

fun executeOnDiskIO(runnable: Runnable) = GlobalScope.launch(Dispatchers.IO) {
    runnable.run()
}

fun executeOnWorkThread(runnable: Runnable) = GlobalScope.launch(Dispatchers.Default) {
    runnable.run()
}

fun verifySdk(version: Int): Boolean = Build.VERSION.SDK_INT >= version