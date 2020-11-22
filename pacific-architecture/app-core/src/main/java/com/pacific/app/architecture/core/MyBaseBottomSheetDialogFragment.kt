package com.pacific.app.architecture.core

import com.pacific.guava.android.mvvm.BaseBottomSheetDialogFragment

abstract class MyBaseBottomSheetDialogFragment : BaseBottomSheetDialogFragment() {

    protected open fun setupWindowInsets() {}
}