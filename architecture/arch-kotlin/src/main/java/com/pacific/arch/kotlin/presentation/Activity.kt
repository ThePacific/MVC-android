package com.pacific.arch.kotlin.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.content.LocalBroadcastManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class Activity : DaggerAppCompatActivity() {
    protected @Inject lateinit var okReceiver: OkBroadcastReceiver
    protected @Inject lateinit var modelFactory: ViewModelFactory

    @Suppress("UNCHECKED_CAST")
    private val realViewModel: ViewModel by lazy {
        ViewModelProviders.of(this, modelFactory).get<ViewModel>(modelClass() as Class<ViewModel>)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filter = IntentFilter()
        addBroadcastAction(filter)
        LocalBroadcastManager.getInstance(this).registerReceiver(okReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        okReceiver.clearConsumer()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(okReceiver)
    }

    @CallSuper
    protected open fun addBroadcastAction(filter: IntentFilter) {
        if (applyFinishAction()) {
            okReceiver.addConsumer(filter,
                    "com.pacific.arch.action.finish",
                    object : OkBroadcastReceiver.Consumer {
                        override fun run(context: Context, intent: Intent) {
                            finish()
                        }
                    })
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun <T : ViewModel> fetchViewModel(): T = realViewModel as T

    protected open fun applyFinishAction(): Boolean {
        return true
    }

    protected abstract fun modelClass(): Class<out ViewModel>
}
