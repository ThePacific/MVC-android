package com.pacific.example

import com.pacific.arch.example.App
import com.pacific.example.base.RxAwareViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(app: App) : RxAwareViewModel(app) {

    fun load() = "Hello World"
}
