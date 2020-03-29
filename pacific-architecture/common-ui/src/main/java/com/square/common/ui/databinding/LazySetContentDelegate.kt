package com.square.common.ui.databinding

import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import kotlin.reflect.KProperty

class LazySetContentDelegate<in T : android.app.Activity, out R : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) {

    private var value: R? = null

    operator fun getValue(ref: T, property: KProperty<*>): R {
        value = value ?: DataBindingUtil.setContentView(ref, layoutRes)
        return value!!
    }
}