package com.square.app

import androidx.multidex.MultiDexApplication
import com.square.core.setupCoreModule

class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        setupCoreModule(
            this,
            BuildConfig.DEBUG,
            BuildConfig.APPLICATION_ID
        )
    }
}