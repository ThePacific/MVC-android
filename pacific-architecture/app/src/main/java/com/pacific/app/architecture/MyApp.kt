package com.pacific.app.architecture

import androidx.multidex.MultiDexApplication
import com.pacific.app.architecture.core.CoreLib

class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        CoreLib.setup(this, BuildConfig.DEBUG)
    }
}