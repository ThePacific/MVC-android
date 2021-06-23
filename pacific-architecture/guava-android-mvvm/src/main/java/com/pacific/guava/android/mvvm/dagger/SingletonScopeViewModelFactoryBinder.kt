package com.pacific.guava.android.mvvm.dagger

import androidx.lifecycle.ViewModelProvider
import com.pacific.guava.android.mvvm.lifecycle.ViewModelFactory
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * ViewModelFactory工厂类，用于单例中
 */
@Module
abstract class SingletonScopeViewModelFactoryBinder {

    @Binds
    @Singleton
    abstract fun provideViewModelFactory(it: ViewModelFactory): ViewModelProvider.Factory
}