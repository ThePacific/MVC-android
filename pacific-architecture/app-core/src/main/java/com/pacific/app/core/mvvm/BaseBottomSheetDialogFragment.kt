package com.pacific.app.core.mvvm

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pacific.app.core.base.AppManager

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    var applyDialogCount = false
        protected set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (applyDialogCount) {
            AppManager.showDialog()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
    }

    override fun onDestroy() {
        super.onDestroy()
        if (applyDialogCount) {
            AppManager.dismissDialog()
        }
    }

    open fun onBackPressed(): Boolean = false
}