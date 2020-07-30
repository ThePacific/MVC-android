package com.pacific.core.dagger

import android.app.Application
import androidx.room.Room
import com.pacific.core.SQL_DB3
import com.pacific.core.storage.db.RoomAppDatabase
import com.pacific.core.storage.prefs.PrefsManager
import com.pacific.data.db.AppDatabase
import com.pacific.data.files.AppPrefsManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [CoreBinder::class])
class CoreModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, RoomAppDatabase::class.java, SQL_DB3).build()
    }

    @Provides
    @Singleton
    fun provideAppPrefsManager(): AppPrefsManager {
        return PrefsManager
    }
}