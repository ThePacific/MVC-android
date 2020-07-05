package com.pacific.core.mvvm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment

abstract class BaseAppCompatDialogFragment : AppCompatDialogFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
    }

    open fun onBackPressed(): Boolean = false
}