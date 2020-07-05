package com.pacific.app.architecture

import androidx.multidex.MultiDexApplication
import com.pacific.core.setupCoreModule

class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        setupCoreModule(this, BuildConfig.DEBUG)
    }
}