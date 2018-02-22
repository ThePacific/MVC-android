package com.pacific.arch.example

import android.content.Context
import android.support.multidex.MultiDex
import com.pacific.arch.views.compact.attachDebug
import com.pacific.example.base.CrashReportingTree
import com.pacific.example.common.DEBUG
import com.pacific.example.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber
import timber.log.Timber.DebugTree

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
        INSTANCE = this

        attachDebug(this, Runnable {
            if (DEBUG) {
                Timber.plant(DebugTree())
            } else {
                Timber.plant(CrashReportingTree())
            }
        })
    }

    fun appComponent() = androidInjector as DaggerAppComponent

    companion object {
        var INSTANCE: App? = null
    }
}