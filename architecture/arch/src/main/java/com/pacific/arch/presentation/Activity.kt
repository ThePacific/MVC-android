package com.pacific.arch.presentation

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.content.LocalBroadcastManager
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class Activity : DaggerAppCompatActivity() {
    @Inject
    lateinit var okBroadcastReceiver: OkBroadcastReceiver

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filter = IntentFilter()
        addBroadcastAction(filter)
        LocalBroadcastManager.getInstance(this).registerReceiver(okBroadcastReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        okBroadcastReceiver.clearConsumer()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(okBroadcastReceiver)
    }

    @CallSuper
    protected open fun addBroadcastAction(filter: IntentFilter) {
        if (applyFinishAction()) {
            okBroadcastReceiver.addConsumer(filter, ACTION_FINISH, { _: Context, _: Intent ->
                finish()
            })
        }
    }

    protected open fun applyFinishAction() = true
}
