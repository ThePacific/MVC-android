package com.pacific.arch.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class Fragment : DaggerFragment() {
    @Inject
    protected lateinit var modelFactory: ViewModelFactory

    @Suppress("UNCHECKED_CAST")
    private val realViewModel: ViewModel? by lazy {
        if (isAttachViewModel()) {
            when (modelProvider()) {
                ViewModelSource.ACTIVITY -> return@lazy ViewModelProviders.of(activity!!, modelFactory)
                        .get<ViewModel>(modelClass() as Class<ViewModel>)
                ViewModelSource.PARENT_FRAGMENT -> return@lazy ViewModelProviders.of(parentFragment!!, modelFactory)
                        .get<ViewModel>(modelClass() as Class<ViewModel>)
                ViewModelSource.NONE -> return@lazy ViewModelProviders.of(this, modelFactory)
                        .get<ViewModel>(modelClass() as Class<ViewModel>)
                else -> throw UnsupportedOperationException()
            }
        }
        null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!view.isClickable) {
            view.isClickable = true
        }
    }

    protected open fun modelProvider(): ViewModelSource {
        return ViewModelSource.ACTIVITY
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T : ViewModel> fetchViewModel(): T = realViewModel as T

    protected open fun isAttachViewModel(): Boolean {
        return true
    }

    protected abstract fun modelClass(): Class<out ViewModel>
}