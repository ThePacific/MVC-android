package com.pacific.app.architecture.core.support.test

import android.os.Bundle
import android.view.View
import com.pacific.app.architecture.core.mvvm.BaseAppCompatDialogFragment

class TestDialogFragment : BaseAppCompatDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}