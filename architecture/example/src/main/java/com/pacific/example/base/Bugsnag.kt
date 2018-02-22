package com.pacific.example.base

import android.util.Log
import timber.log.Timber

interface CrashLibrary {
    fun log(priority: Int, tag: String, message: String)
    fun logWarning(t: Throwable)
    fun logError(t: Throwable)
}


object FakeCrashLibrary : CrashLibrary {

    override fun log(priority: Int, tag: String, message: String) {
    }

    override fun logWarning(t: Throwable) {
    }

    override fun logError(t: Throwable) {
    }
}

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG) {
            return
        }

        FakeCrashLibrary.log(priority, tag!!, message)

        if (t != null) {
            if (priority == Log.ERROR) {
                FakeCrashLibrary.logError(t)
            } else if (priority == Log.WARN) {
                FakeCrashLibrary.logWarning(t)
            }
        }

    }
}