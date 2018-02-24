package com.pacific.example

import android.arch.lifecycle.ViewModel
import com.pacific.arch.example.App
import javax.inject.Inject

class AppViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var app: App
}