package com.pacific.example.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module(includes = [(AndroidSupportInjectionModule::class), (AppBinder::class)])
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        Log.e("0_______", "0_______")
        return OkHttpClient.Builder().build()
    }
}