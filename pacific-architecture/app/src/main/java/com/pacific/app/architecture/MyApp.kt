package com.pacific.app.architecture

import androidx.multidex.MultiDexApplication
import com.pacific.app.architecture.core.CoreX

class MyApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        CoreX.setup(this, BuildConfig.DEBUG)
    }
}