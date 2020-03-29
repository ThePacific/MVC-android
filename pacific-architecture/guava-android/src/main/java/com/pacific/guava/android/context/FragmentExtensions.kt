package com.pacific.guava.android.context

import androidx.fragment.app.Fragment
import com.pacific.guava.android.ime.ImeUtils

fun Fragment.instantiate(className: String): Fragment {
    return childFragmentManager.fragmentFactory.instantiate(
        activity?.classLoader ?: javaClass.classLoader!!,
        className
    )
}

fun Fragment.hideSoftInput() = this.view?.let { ImeUtils.hideIme(it) }