package com.pacific.app.data

import com.pacific.app.data.base.AppContext
import com.pacific.app.data.db.AppDatabase
import com.pacific.app.data.file.AppPrefsManager

@get:JvmName("dataComponent")
lateinit var dataComponent: DataComponent
    private set

val hosts = mapOf(
    100 to "https://www.google.com/",
    200 to "https://www.google.com/",
    300 to "https://www.google.com/"
)

fun setupDataModule(
    appContext: AppContext,
    appDatabase: AppDatabase,
    appPrefsManager: AppPrefsManager
) {
    dataComponent = DaggerDataComponent.factory()
        .create(
        appContext,
        appDatabase,
        appPrefsManager
    )
}