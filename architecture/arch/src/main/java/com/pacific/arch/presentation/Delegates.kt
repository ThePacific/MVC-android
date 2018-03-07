package com.pacific.arch.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup
import kotlin.reflect.KProperty

typealias OkConsumer = (context: Context, intent: Intent) -> Unit

fun <T : Activity, R : ViewDataBinding> contentView(@LayoutRes layoutRes: Int) = SetContentView<T, R>(layoutRes)

class SetContentView<in T : Activity, out R : ViewDataBinding>(@LayoutRes private val layoutRes: Int) {
    private var value: R? = null
    operator fun getValue(thisRef: T, property: KProperty<*>): R {
        value = value ?: DataBindingUtil.setContentView(thisRef, layoutRes)
        return value!!
    }
}

////used in Fragment or DialogFragment
fun <T : ViewDataBinding> LayoutInflater.fragmentDataBinding(@LayoutRes layoutRes: Int, container: ViewGroup?): T {
    return DataBindingUtil.inflate(this, layoutRes, container, false)
}

fun <T : Activity, R : ViewModel> activityViewModel(modelClass: Class<R>) = SetActivityViewModel<T, R>(modelClass)

class SetActivityViewModel<in T : Activity, out R : ViewModel>(private val modelClass: Class<R>) {
    private var value: R? = null
    operator fun getValue(thisRef: T, property: KProperty<*>): R {
        value = value ?: ViewModelProviders.of(thisRef, thisRef.viewModelFactory).get<R>(modelClass)
        return value!!
    }
}

fun <T : Fragment, R : ViewModel> fragmentViewModel(modelClass: Class<R>) = SetFragmentViewModel<T, R>(modelClass)

class SetFragmentViewModel<in T : Fragment, out R : ViewModel>(private val modelClass: Class<R>) {
    private var value: R? = null
    operator fun getValue(thisRef: T, property: KProperty<*>): R {
        if (value == null && thisRef.isAttachViewModel()) {
            value = when (thisRef.viewModelSource()) {
                ViewModelSource.ACTIVITY -> {
                    ViewModelProviders.of(thisRef.activity!!, thisRef.viewModelFactory).get(modelClass)
                }
                ViewModelSource.PARENT_FRAGMENT -> {
                    ViewModelProviders.of(thisRef.parentFragment!!, thisRef.viewModelFactory).get(modelClass)
                }
                ViewModelSource.NONE -> {
                    ViewModelProviders.of(thisRef, thisRef.viewModelFactory).get(modelClass)
                }
            }
        }
        return value!!
    }
}

fun <T : AppCompatDialogFragment, R : ViewModel> dialogFragmentViewModel(modelClass: Class<R>) = SetDialogFragmentViewModel<T, R>(modelClass)

class SetDialogFragmentViewModel<in T : AppCompatDialogFragment, out R : ViewModel>(private val modelClass: Class<R>) {
    private var value: R? = null
    operator fun getValue(thisRef: T, property: KProperty<*>): R {
        if (value == null && thisRef.isAttachViewModel()) {
            value = when (thisRef.viewModelSource()) {
                ViewModelSource.ACTIVITY -> {
                    ViewModelProviders.of(thisRef.activity!!, thisRef.viewModelFactory).get(modelClass)
                }
                ViewModelSource.PARENT_FRAGMENT -> {
                    ViewModelProviders.of(thisRef.parentFragment!!, thisRef.viewModelFactory).get(modelClass)
                }
                ViewModelSource.NONE -> {
                    ViewModelProviders.of(thisRef, thisRef.viewModelFactory).get(modelClass)
                }
            }
        }
        return value!!
    }
}



