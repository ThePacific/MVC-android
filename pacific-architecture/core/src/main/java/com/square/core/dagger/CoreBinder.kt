package com.square.core.dagger

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.square.core.initializer.AppInitializer
import com.square.core.initializer.ThreeTenBpInitializer
import com.square.core.initializer.TimberInitializer
import com.square.core.lifecycle.ViewModelFactory
import com.square.core.storage.db.RoomAppDatabase
import com.square.data.db.AppDatabase
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
    @Singleton
    abstract fun provideViewModelFactory(it: ViewModelFactory): ViewModelProvider.Factory

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