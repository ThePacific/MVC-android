package com.pacific.guava.android.mvvm.dagger

import androidx.lifecycle.ViewModelProvider

/**
 * ViewModelFactory接口
 */
interface ViewModelFactoryComponent {

    fun viewModelFactory(): ViewModelProvider.Factory
}