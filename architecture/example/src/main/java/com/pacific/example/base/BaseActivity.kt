package com.pacific.example.base

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import com.pacific.arch.gauva.Preconditions2
import com.pacific.arch.presentation.Activity
import com.pacific.example.AppInitializer
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable
import javax.inject.Inject

abstract class BaseActivity : Activity() {
    private var deep = -1
    @Inject
    lateinit var appInitializer: AppInitializer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appDeep++
        deep = appDeep
        appInitializer.appLifecycle.autoDisposable(scope(Lifecycle.Event.ON_DESTROY)).subscribe {
            onAppLifecycle(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appDeep--
        deep = -1
    }

    protected open fun onAppLifecycle(event: Lifecycle.Event) {
    }

    fun isTopActivity(): Boolean {
        Preconditions2.checkState(deep <= appDeep)
        return deep == appDeep
    }

    companion object {
        @JvmStatic
        protected var appDeep = 0
    }
}