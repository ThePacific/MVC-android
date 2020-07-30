package com.pacific.data.test

import androidx.annotation.VisibleForTesting
import com.pacific.data.DataComponent
import com.pacific.data.DataModule
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

        fun create(): TestComponent
    }
}