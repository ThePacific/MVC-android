package com.pacific.core.initializer

import android.app.Application
import com.pacific.guava.Guava
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor() : AppInitializer {

    override fun initialize(app: Application) {
        if (Guava.isDebug) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(BugTree())
        }
    }

    private class BugTree : Timber.Tree() {

        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            // CrashLibrary.log(message)
        }
    }
}