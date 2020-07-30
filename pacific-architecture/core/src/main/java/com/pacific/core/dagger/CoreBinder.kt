package com.pacific.core.dagger

import android.app.Application
import android.content.Context
import com.pacific.core.initializer.AppInitializer
import com.pacific.core.initializer.TimberInitializer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
abstract class CoreBinder {

    @Binds
    @Singleton
    abstract fun provideContext(it: Application): Context

    @Binds
    @IntoSet
    abstract fun provideTimberInitializer(it: TimberInitializer): AppInitializer

    // @Binds
    // @Singleton
    // abstract fun provideVewModelFactory(it: ViewModelFactory): ViewModelProvider.Factory
}