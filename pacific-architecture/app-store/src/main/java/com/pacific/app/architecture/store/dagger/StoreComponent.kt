package com.pacific.app.architecture.store.dagger

import com.pacific.app.architecture.store.db.PlatformDatabase
import com.pacific.app.architecture.store.file.PlatformPrefs
import com.pacific.app.architecture.store.http.service.DataService
import com.pacific.guava.data.DataComponent
import com.pacific.guava.data.PlatformContext
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        StoreModule::class
    ]
)
@Singleton
interface StoreComponent : DataComponent {

    fun dataService(): DataService

    fun platformDatabase(): PlatformDatabase

    fun platformPrefs(): PlatformPrefs

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance platformDatabase: PlatformDatabase,
            @BindsInstance platformContext: PlatformContext,
            @BindsInstance platformPrefs: PlatformPrefs
        ): StoreComponent
    }
}