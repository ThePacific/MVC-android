package com.pacific.arch.example

import android.content.Context
import android.support.multidex.MultiDex
import android.util.Log
import com.pacific.example.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import okhttp3.OkHttpClient
import javax.inject.Inject

class App : DaggerApplication() {
    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.e("3_______", okHttpClient.toString())
    }

    companion object {
        var instance: App? = null
    }
}