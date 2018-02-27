package com.pacific.example.feature.main

import com.pacific.arch.presentation.ViewModelSource
import com.pacific.example.base.BaseFragment
import javax.inject.Inject

abstract class MainFragment : BaseFragment() {
    @Inject
    lateinit var mainActivity: MainActivity

    override fun viewModelSource(): ViewModelSource {
        return ViewModelSource.NONE
    }
}
