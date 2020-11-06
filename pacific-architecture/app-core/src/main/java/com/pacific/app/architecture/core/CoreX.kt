package com.pacific.app.architecture.core

import android.app.Application
import android.os.StrictMode
import androidx.room.Room
import com.pacific.app.architecture.core.base.AppDatabase
import com.pacific.app.architecture.core.base.AppPrefs
import com.pacific.app.architecture.core.base.BugTree
import com.pacific.app.architecture.store.StoreX
import com.pacific.guava.android.mvvm.AndroidX
import com.pacific.guava.android.mvvm.AppContext
import com.pacific.guava.android.mvvm.AppManager
import timber.log.Timber

object CoreX {

    fun setup(app: Application, isDebug: Boolean) {
        if (isDebug) {
            enableStrictMode()
        } else {
            Timber.plant(BugTree())
        }

        AndroidX.setup(app, isDebug)
        AppManager.initialize()

        StoreX.setup(createAppDatabase(), AppContext(), AppPrefs)
    }

    private fun createAppDatabase(): AppDatabase {
        return Room.databaseBuilder(AndroidX.myApp, AppDatabase::class.java, "coreX_v1.db3")
            .addCallback(AppDatabase.DbCallback())
            .addMigrations(AppDatabase.DbMigration(1, 1))
            .build()
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

