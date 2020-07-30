package com.pacific.core.dagger

import android.app.Application
import android.content.Context
import com.pacific.core.initializer.AppInitializerManager
import com.pacific.data.DataComponent
import com.pacific.data.DataModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        CoreModule::class,
        DataModule::class
    ]
)
@Singleton
interface CoreComponent : DataComponent {

    fun appInitializerManager(): AppInitializerManager

    fun context(): Context

    // fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance app: Application
        ): CoreComponent
    }
}