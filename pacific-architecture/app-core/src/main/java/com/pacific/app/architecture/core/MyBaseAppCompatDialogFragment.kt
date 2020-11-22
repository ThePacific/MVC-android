package com.pacific.app.architecture.core

import com.pacific.guava.android.mvvm.BaseAppCompatDialogFragment

abstract class MyBaseAppCompatDialogFragment : BaseAppCompatDialogFragment() {

    protected open fun setupWindowInsets() {}
}