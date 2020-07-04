package com.pacific.core.dagger

import android.app.Application
import android.content.Context
import com.pacific.core.initializer.AppInitializer
import com.pacific.core.initializer.ThreeTenBpInitializer
import com.pacific.core.initializer.TimberInitializer
import com.pacific.core.storage.db.RoomAppDatabase
import com.pacific.data.db.AppDatabase
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
abstract class CoreBinder {

    @Binds
    @Singleton
    abstract fun provideContext(it: Application): Context

    // @Binds
    // @Singleton
    // abstract fun provideViewModelFactory(it: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @Singleton
    abstract fun provideAppDatabase(it: RoomAppDatabase): AppDatabase

    @Binds
    @IntoSet
    abstract fun provideThreeTenBpInitializer(it: ThreeTenBpInitializer): AppInitializer

    @Binds
    @IntoSet
    abstract fun provideTimberInitializer(it: TimberInitializer): AppInitializer
}