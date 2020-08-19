package com.pacific.app.architecture.core.dagger

import androidx.lifecycle.ViewModelProvider

interface ViewModelFactoryProvider {

    fun viewModelFactory(): ViewModelProvider.Factory
}