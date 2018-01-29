package com.pacific.arch.presentation

import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class Fragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!view.isClickable) {
            view.isClickable = true
        }
    }

    open fun viewModelSource() = ViewModelSource.ACTIVITY

    open fun isAttachViewModel() = true
}