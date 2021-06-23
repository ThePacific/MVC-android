package com.pacific.guava.android.mvvm.lifecycle

import android.content.res.Configuration

/**
 * 生命周期回调函数融合了activity和fragment
 */
interface ViewLifecycle {

    fun onCreate()

    fun onCreateView()

    fun onViewCreated()

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroyView()

    fun onDestroy()

    fun onConfigurationChanged(newConfig: Configuration)
}