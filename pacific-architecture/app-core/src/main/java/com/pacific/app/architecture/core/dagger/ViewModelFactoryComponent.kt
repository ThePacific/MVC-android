package com.pacific.app.architecture.core.dagger

import androidx.lifecycle.ViewModelProvider

interface ViewModelFactoryComponent {

    fun viewModelFactory(): ViewModelProvider.Factory
}