package com.pacific.arch.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class AppCompatDialogFragment : android.support.v7.app.AppCompatDialogFragment() {
    @Inject
    lateinit var modelFactory: ViewModelFactory

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!view.isClickable) {
            view.isClickable = true
        }
    }


    open fun modelProvider() = ViewModelSource.ACTIVITY

    open fun isAttachViewModel() = true
}
