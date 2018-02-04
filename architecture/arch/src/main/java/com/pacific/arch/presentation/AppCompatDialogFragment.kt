package com.pacific.arch.presentation

import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerAppCompatDialogFragment
import javax.inject.Inject

abstract class AppCompatDialogFragment : DaggerAppCompatDialogFragment() {
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
