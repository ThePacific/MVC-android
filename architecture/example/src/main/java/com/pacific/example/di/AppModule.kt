package com.pacific.example.di

import com.pacific.adapter.RecyclerAdapter
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule

@Module(includes = [(AndroidSupportInjectionModule::class), (AppBinder::class)])
class AppModule {
    @Provides
    fun provideRecyclerAdapter() = RecyclerAdapter()
}