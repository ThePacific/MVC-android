package com.pacific.example.base

import android.arch.lifecycle.Lifecycle
import android.content.Intent
import android.os.Bundle
import com.pacific.arch.gauva.Preconditions2
import com.pacific.arch.presentation.Activity
import com.pacific.example.AppInitializer
import com.pacific.example.data.APP_LIFECYCLE
import com.pacific.example.data.APP_UI_DEEP
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable
import javax.inject.Inject

abstract class BaseActivity : Activity() {
    private var uiDeep = -1

    @Inject
    lateinit var appInitializer: AppInitializer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        APP_UI_DEEP++
        uiDeep = APP_UI_DEEP
        APP_LIFECYCLE.autoDisposable(scope(Lifecycle.Event.ON_DESTROY)).subscribe {
            onAppLifecycle(it)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    open fun handleIntent(intent: Intent) {}

    override fun onDestroy() {
        super.onDestroy()
        APP_UI_DEEP--
        uiDeep = -1
    }

    protected open fun onAppLifecycle(event: Lifecycle.Event) {
    }

    fun isTopActivity(): Boolean {
        Preconditions2.checkState(uiDeep <= APP_UI_DEEP)
        return uiDeep == APP_UI_DEEP
    }
}