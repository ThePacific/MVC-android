package com.pacific.example

import com.pacific.arch.example.App
import javax.inject.Inject

class MainViewModel @Inject constructor(app: App) : BaseViewModel(app) {

    fun load(userId: Int) = "Hello World"
}
