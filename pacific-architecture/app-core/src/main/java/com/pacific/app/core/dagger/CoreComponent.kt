package com.pacific.app.core.dagger

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.pacific.app.data.DataComponent
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        CoreModule::class,
        CoreBinder::class
    ],
    dependencies = [
        DataComponent::class
    ]
)
@AppScope
interface CoreComponent {

    fun context(): Context

    fun viewModelFactory(): ViewModelProvider.Factory

    @Component.Factory
    interface Factory {

        fun create(
            dataComponent: DataComponent,
            @BindsInstance app: Application
        ): CoreComponent
    }
}