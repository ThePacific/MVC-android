package com.pacific.example

import android.content.Context
import android.support.multidex.MultiDex
import com.pacific.example.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class App : DaggerApplication() {
    @Inject
    lateinit var appInitializer: AppInitializer

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
        appInitializer.onAppCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        appInitializer.onAppTerminate()
    }

    fun appComponent() = androidInjector as DaggerAppComponent

    companion object {
        @Volatile
        private var INSTANCE: App? = null

        fun get() = INSTANCE!!
    }
}