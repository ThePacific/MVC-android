package com.pacific.arch.example

import android.content.Context
import android.support.multidex.MultiDex
import com.baidu.mapapi.SDKInitializer
import com.pacific.example.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        SDKInitializer.initialize(this)
    }
}