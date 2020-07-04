package com.pacific.guava.android.context

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.pacific.guava.android.ime.ImeUtils

fun FragmentActivity.instantiate(className: String): Fragment {
    return supportFragmentManager.fragmentFactory.instantiate(classLoader, className)
}

fun Activity.hideSoftInput() = ImeUtils.hideIme(this.window.decorView)