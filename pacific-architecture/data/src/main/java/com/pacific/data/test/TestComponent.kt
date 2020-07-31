package com.pacific.data.test

import androidx.annotation.VisibleForTesting
import com.pacific.data.DataComponent
import com.pacific.data.DataModule
import com.pacific.data.db.AppDatabase
import com.pacific.data.files.AppPrefsManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@VisibleForTesting
@Component(
    modules = [
        TestModule::class,
        DataModule::class
    ]
)
@Singleton
interface TestComponent : DataComponent {

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance appDatabase: AppDatabase,
            @BindsInstance appPrefsManager: AppPrefsManager
        ): TestComponent
    }
}