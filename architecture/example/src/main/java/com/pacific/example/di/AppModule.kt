package com.pacific.example.di

import android.util.Log
import com.pacific.example.GlideComponent
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Module(includes = [(AndroidSupportInjectionModule::class), (AppBinder::class)],
        subcomponents = [(GlideComponent::class)])
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        Log.e("_________________","init OkHttpClient")
        return OkHttpClient.Builder().build()
    }
}