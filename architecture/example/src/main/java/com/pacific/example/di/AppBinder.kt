package com.pacific.example.di

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.pacific.arch.example.App
import com.pacific.arch.example.MainActivity
import com.pacific.arch.presentation.ViewModelFactory
import com.pacific.arch.presentation.ViewModelKey
import com.pacific.example.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class AppBinder {
    ////APP binders
    @Singleton
    @Binds
    abstract fun bindViewModelFactory(it: ViewModelFactory): ViewModelProvider.Factory

    @Singleton
    @Binds
    abstract fun provideApplication(it: App): Application

    @Singleton
    @Binds
    abstract fun provideContext(it: App): Context


    ////ViewModel binders
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(it: MainViewModel): ViewModel


    ////Activity binders
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity


    ////the global Fragment and FragmentDialog binders
}

