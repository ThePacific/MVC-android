package com.pacific.app.core

import android.app.Application
import android.os.StrictMode
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.pacific.app.core.base.*
import com.pacific.app.core.dagger.CoreComponent
import com.pacific.app.core.dagger.DaggerCoreComponent
import com.pacific.app.data.dataComponent
import com.pacific.app.data.setupDataModule
import com.pacific.guava.jvm.Guava
import timber.log.Timber

const val APK_PACKAGE_ARCHIVE_TYPE = "application/vnd.android.package-archive"
const val SQL_DB3 = "sql.db3"
const val ASSETS = "file:///android_asset/"

const val BUS_EXIT_APP = 900
const val BUS_DIALOG_COUNT = 901

@get:JvmName("myApp")
lateinit var myApp: Application
    private set

@get:JvmName("coreComponent")
lateinit var coreComponent: CoreComponent
    private set

@get:JvmName("appDialogCount")
val appDialogCount: MutableLiveData<Int> by lazy { MutableLiveData(0) }

val isNetworkConnected: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }

val isAppInForeground: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }

fun setupCoreModule(app: Application, isDebug: Boolean) {
    myApp = app

    Guava.isDebug = isDebug
    Guava.timber = AndroidTimber

    setupDataModule(AndroidContext, createRoomDatabase(), PrefsManager)
    coreComponent = DaggerCoreComponent.factory().create(dataComponent, app)

    initializeTimber()
    AppManager.initialize(app)
    if (isDebug) {
        enableStrictMode()
    }
}

private fun createRoomDatabase(): RoomAppDatabase {
    return Room.databaseBuilder(myApp, RoomAppDatabase::class.java, SQL_DB3)
        .addCallback(RoomDatabaseCallback())
        .addMigrations()
        .build()
}

private fun initializeTimber() {
    if (Guava.isDebug) {
        Timber.plant(Timber.DebugTree())
    } else {
        Timber.plant(BugTree())
    }
}

private fun enableStrictMode() {
    StrictMode.setThreadPolicy(
        StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build()
    )
    StrictMode.setVmPolicy(
        StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build()
    )
}