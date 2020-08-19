package com.pacific.app.architecture.core

import android.app.Application
import android.os.StrictMode
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.pacific.app.architecture.core.base.*
import com.pacific.app.architecture.core.dagger.CoreComponent
import com.pacific.app.architecture.core.dagger.DaggerCoreComponent
import com.pacific.app.architecture.data.DataLib
import com.pacific.guava.jvm.Guava
import timber.log.Timber

object CoreLib {

    const val APK_PACKAGE_ARCHIVE_TYPE = "application/vnd.android.package-archive"
    const val SQL_DB3 = "sql.db3"
    const val ASSETS = "file:///android_asset/"

    const val BUS_EXIT_APP = 900
    const val BUS_DIALOG_COUNT = 901

    @get:JvmName("appDialogCount")
    val appDialogCount: MutableLiveData<Int> = MutableLiveData(0)

    @get:JvmName("isNetworkConnected")
    val isNetworkConnected: MutableLiveData<Boolean> = MutableLiveData(false)

    @get:JvmName("isAppInForeground")
    val isAppInForeground: MutableLiveData<Boolean> = MutableLiveData(false)

    @get:JvmName("myApp")
    lateinit var myApp: Application
        private set

    @get:JvmName("component")
    lateinit var component: CoreComponent
        private set

    fun setup(app: Application, isDebug: Boolean) {
        myApp = app

        Guava.isDebug = isDebug
        Guava.timber = AndroidTimber

        DataLib.setup(AndroidContext, createRoomDatabase(), PrefsManager)
        component = DaggerCoreComponent.factory().create(DataLib.component, app)

        initializeTimber()
        AppManager.initialize()

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
}

