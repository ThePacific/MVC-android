package com.pacific.data

import com.pacific.data.base.AppContext
import com.pacific.data.db.AppDatabase
import com.pacific.data.file.AppPrefsManager

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
    dataComponent = DaggerDataComponent.factory().create(
        appContext,
        appDatabase,
        appPrefsManager
    )
}