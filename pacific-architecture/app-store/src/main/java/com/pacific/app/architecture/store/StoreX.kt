package com.pacific.app.architecture.store

import com.pacific.app.architecture.store.dagger.DaggerStoreComponent
import com.pacific.app.architecture.store.dagger.StoreComponent
import com.pacific.app.architecture.store.db.PlatformDatabase
import com.pacific.app.architecture.store.file.PlatformPrefs
import com.pacific.guava.data.PlatformContext

object StoreX {

    @get:JvmName("component")
    lateinit var component: StoreComponent
        private set

    val hosts = mapOf(
        100 to "https://www.google.com/",
        200 to "https://www.google.com/",
        300 to "https://www.google.com/"
    )

    fun setup(
        platformDatabase: PlatformDatabase,
        platformContext: PlatformContext,
        platformPrefs: PlatformPrefs
    ) {
        if (::component.isInitialized) {
            return
        }

        component = DaggerStoreComponent.factory().create(
            platformDatabase,
            platformContext,
            platformPrefs
        )
    }
}

