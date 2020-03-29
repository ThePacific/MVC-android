package com.square.common.ui.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

@JvmOverloads
fun <T : ViewDataBinding> LayoutInflater.dataBinding(
    @LayoutRes layoutRes: Int,
    container: ViewGroup? = null
): T {
    return DataBindingUtil.inflate(this, layoutRes, container, false)
}


fun <T : android.app.Activity, R : ViewDataBinding> dataBinding(
    @LayoutRes layoutRes: Int
): LazySetContentDelegate<T, R> {
    return LazySetContentDelegate<T, R>(layoutRes)
}