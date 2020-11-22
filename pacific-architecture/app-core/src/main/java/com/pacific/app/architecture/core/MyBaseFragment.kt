package com.pacific.app.architecture.core

import com.pacific.guava.android.mvvm.BaseFragment

abstract class MyBaseFragment : BaseFragment() {

    override fun onResume() {
        super.onResume()
        val activity = requireActivity()
        if (activity is MyBaseActivity) {
            activity.fragment = this
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val activity = requireActivity()
        if (activity is MyBaseActivity && this == activity.fragment) {
            activity.fragment = null
        }
    }

    protected open fun setupWindowInsets() {}
}