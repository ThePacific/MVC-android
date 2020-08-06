package com.pacific.app.core.dagger

import androidx.lifecycle.ViewModelProvider

interface ViewModelFactoryProvider {

    fun viewModelFactory(): ViewModelProvider.Factory
}