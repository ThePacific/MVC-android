package com.pacific.arch.example

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.util.Log
import com.pacific.arch.presentation.Activity
import com.pacific.example.MainViewModel
import javax.inject.Inject

class MainActivity : Activity() {

    @Inject
    lateinit var app: App

    private val model: MainViewModel by lazy {
        fetchViewModel() as MainViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("_______________", app.toString())
        Log.e("_______________", model.toString())
    }

    override fun modelClass(): Class<out ViewModel> = MainViewModel::class.java
}
