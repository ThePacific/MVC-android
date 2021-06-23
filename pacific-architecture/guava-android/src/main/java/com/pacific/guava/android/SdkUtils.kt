package com.pacific.guava.android

import android.os.Build
import android.os.Looper
import io.reactivex.rxjava3.android.MainThreadDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * 是否UI线程
 */
fun isMainThread() = Looper.getMainLooper().thread == Thread.currentThread()

/**
 * 强制UI线程
 */
fun ensureMainThread() = MainThreadDisposable.verifyMainThread()

/**
 * 强制工作线程
 */
fun ensureWorkThread() {
    check(!isMainThread()) { "Expected to be called on work thread" }
}

/**
 * 转到UI线程
 */
fun postToMainThread(runnable: Runnable) = GlobalScope.launch(Dispatchers.Main) {
    runnable.run()
}

/**
 * 转到IO线程
 */
fun executeOnDiskIO(runnable: Runnable) = GlobalScope.launch(Dispatchers.IO) {
    runnable.run()
}

/**
 * 转到工作线程
 */
fun executeOnWorkThread(runnable: Runnable) = GlobalScope.launch(Dispatchers.Default) {
    runnable.run()
}

/**
 * 判断android sdk版本
 */
fun verifySdk(version: Int): Boolean = Build.VERSION.SDK_INT >= version