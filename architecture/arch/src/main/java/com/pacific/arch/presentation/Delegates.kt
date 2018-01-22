package com.pacific.arch.presentation

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import kotlin.reflect.KProperty

interface GetViewModel {
    @Suppress("UNCHECKED_CAST")
    fun <T : ViewModel> getViewModel(): T
}

////delegate DataBindingUtil.setContentView() in Activity
fun <T : Activity, R : ViewDataBinding> contentView(@LayoutRes layoutRes: Int) = SetContentView<T, R>(layoutRes)

class SetContentView<in T : Activity, out R : ViewDataBinding>(@LayoutRes private val layoutRes: Int) {
    private var value: R? = null
    operator fun getValue(thisRef: T, property: KProperty<*>): R {
        if (value == null) {
            value = DataBindingUtil.setContentView(thisRef, layoutRes)
        }
        return value!!
    }
}


////delegate getViewModel
fun <T : GetViewModel, R : ViewModel> viewModel() = SetViewModel<T, R>()

class SetViewModel<in T : GetViewModel, out R : ViewModel> {
    private var value: R? = null
    operator fun getValue(thisRef: T, property: KProperty<*>): R {
        if (value == null) {
            value = thisRef.getViewModel()
        }
        return value!!
    }
}