package com.pacific.arch.presentation

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.util.ArrayMap
import javax.inject.Inject

class OkBroadcastReceiver @Inject constructor() : BroadcastReceiver() {
    private val consumers = ArrayMap<String, OkConsumer>()

    override fun onReceive(context: Context, intent: Intent) {
        for ((key, value) in consumers) {
            if (key == intent.action) {
                value(context, intent)
                break
            }
        }
    }

    fun addConsumer(filter: IntentFilter, action: String, consumer: OkConsumer): OkBroadcastReceiver {
        filter.addAction(action)
        consumers[action] = consumer
        return this
    }

    fun clearConsumer(): OkBroadcastReceiver {
        consumers.clear()
        return this
    }

    fun removeConsumer(action: String): OkBroadcastReceiver {
        consumers.remove(action)
        return this
    }
}