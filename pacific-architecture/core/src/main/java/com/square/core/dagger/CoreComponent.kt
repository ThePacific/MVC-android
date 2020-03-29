package com.square.core.dagger

import android.app.Application
import android.content.Context
import com.square.core.initializer.AppInitializerManager
import com.square.core.storage.db.RoomAppDatabase
import com.square.data.http.DataHttpComponent
import com.square.data.http.DataHttpModule
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