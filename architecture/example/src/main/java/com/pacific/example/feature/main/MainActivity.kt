package com.pacific.arch.example

import android.os.Bundle
import com.pacific.arch.example.databinding.ActivityMainBinding
import com.pacific.arch.presentation.Activity
import com.pacific.arch.presentation.activityViewModel
import com.pacific.arch.presentation.contentView
import com.pacific.arch.presentation.replaceFragment
import com.pacific.example.MainFragment
import com.pacific.example.MainViewModel
import timber.log.Timber

class MainActivity : Activity() {

    private val model by activityViewModel(MainViewModel::class.java)
    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(this, MainFragment.newInstance(), false, binding.container.id)
        Timber.e(toString())
    }
}
