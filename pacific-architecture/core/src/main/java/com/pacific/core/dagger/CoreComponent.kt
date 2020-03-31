package com.pacific.core.dagger

import android.app.Application
import android.content.Context
import com.pacific.data.http.DataHttpComponent
import com.pacific.data.http.DataHttpModule
import com.pacific.core.initializer.AppInitializerManager
import com.pacific.core.storage.db.RoomAppDatabase
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        CoreModule::class,
        DataHttpModule::class
    ]
)
@Singleton
interface CoreComponent : DataHttpComponent {

    fun provideAppInitializerManager(): AppInitializerManager

    fun provideContext(): Context

    fun provideAppDatabase(): RoomAppDatabase

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance app: Application): CoreComponent
    }
}