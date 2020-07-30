package com.pacific.data.test

import androidx.annotation.VisibleForTesting
import com.pacific.data.db.AppDatabase
import com.pacific.data.files.AppPrefsManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@VisibleForTesting
@Module(includes = [TestBinder::class])
class TestModule {

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return TestAppDatabase
    }

    @Provides
    @Singleton
    fun provideAppPrefsManager(): AppPrefsManager {
        return TestPrefsManager
    }
}