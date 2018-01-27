package com.pacific.arch.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import kotlin.reflect.KProperty

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


fun <T : Activity, R : ViewModel>
        activityViewModel(modelClass: Class<R>) = SetActivityViewModel<T, R>(modelClass)

class SetActivityViewModel<in T : Activity, out R : ViewModel>(private val modelClass: Class<R>) {
    private var value: R? = null
    operator fun getValue(thisRef: T, property: KProperty<*>): R {
        if (value == null) {
            value = ViewModelProviders.of(thisRef, thisRef.modelFactory).get<R>(modelClass)
        }
        return value!!
    }
}

fun <T : android.support.v4.app.Fragment, R : ViewModel>
        fragmentViewModel(modelClass: Class<R>) = SetFragmentViewModel<T, R>(modelClass)

class SetFragmentViewModel<in T : android.support.v4.app.Fragment, out R : ViewModel>(private val modelClass: Class<R>) {
    private var value: R? = null
    operator fun getValue(thisRef: T, property: KProperty<*>): R {
        if (value == null) {
            when (thisRef) {
                is Fragment -> {
                    val f = thisRef as Fragment
                    if (thisRef.isAttachViewModel()) {
                        value = when (thisRef.modelProvider()) {
                            ViewModelSource.ACTIVITY -> {
                                ViewModelProviders.of(f.activity!!, f.modelFactory).get(modelClass)
                            }
                            ViewModelSource.PARENT_FRAGMENT -> {
                                ViewModelProviders.of(f.parentFragment!!, f.modelFactory).get(modelClass)
                            }
                            ViewModelSource.NONE -> {
                                ViewModelProviders.of(f, f.modelFactory).get(modelClass)
                            }
                        }
                    }
                }
                is AppCompatDialogFragment -> {
                    val f = thisRef as AppCompatDialogFragment
                    if (thisRef.isAttachViewModel()) {
                        value = when (thisRef.modelProvider()) {
                            ViewModelSource.ACTIVITY -> {
                                ViewModelProviders.of(f.activity!!, f.modelFactory).get(modelClass)
                            }
                            ViewModelSource.PARENT_FRAGMENT -> {
                                ViewModelProviders.of(f.parentFragment!!, f.modelFactory).get(modelClass)
                            }
                            ViewModelSource.NONE -> {
                                ViewModelProviders.of(f, f.modelFactory).get(modelClass)
                            }
                        }
                    }
                }
                else -> {
                    throw UnsupportedOperationException()
                }
            }
        }
        return value!!
    }
}



