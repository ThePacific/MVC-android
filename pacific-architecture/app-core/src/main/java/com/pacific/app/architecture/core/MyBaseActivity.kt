package com.pacific.app.architecture.core

import androidx.fragment.app.Fragment
import com.pacific.guava.android.mvvm.BaseActivity

abstract class MyBaseActivity : BaseActivity() {

    var fragment: Fragment? = null

    protected open fun setupWindowInsets() {}
}