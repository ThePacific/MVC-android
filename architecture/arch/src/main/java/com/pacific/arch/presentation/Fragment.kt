package com.pacific.arch.presentation

import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class Fragment : DaggerFragment() {
    @Inject
    lateinit var modelFactory: ViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!view.isClickable) {
            view.isClickable = true
        }
    }

    open fun modelProvider() = ViewModelSource.ACTIVITY

    open fun isAttachViewModel() = true
}