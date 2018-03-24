package com.pacific.example.views

import android.content.Context
import android.support.v4.view.ActionProvider
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import com.pacific.example.R

@SuppressWarnings("unused")
class SyncActionProvider(context: Context) : ActionProvider(context) {

    override fun onCreateActionView(): View {
        return onCreateActionView(null)
    }

    override fun onCreateActionView(forItem: MenuItem?): View {
        val layoutInflater = LayoutInflater.from(context)
        return layoutInflater.inflate(R.layout.action_sync, null)
    }
}