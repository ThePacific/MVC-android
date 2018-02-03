package com.pacific.arch.example

import android.content.Context
import android.support.multidex.MultiDex
import com.pacific.example.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {
    private val androidInjector: AndroidInjector<out DaggerApplication> by lazy {
        DaggerAppComponent.builder().create(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return androidInjector
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun appComponent() = androidInjector as DaggerAppComponent

    companion object {
        var instance: App? = null
    }
}