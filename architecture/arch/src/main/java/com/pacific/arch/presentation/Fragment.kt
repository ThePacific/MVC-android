package com.pacific.arch.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class Fragment : DaggerFragment(), GetViewModel {
    @Inject
    lateinit var modelFactory: ViewModelFactory

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

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> getViewModel() = realViewModel as T

    protected open fun modelProvider() = ViewModelSource.ACTIVITY

    protected open fun isAttachViewModel() = true

    protected abstract fun modelClass(): Class<out ViewModel>
}