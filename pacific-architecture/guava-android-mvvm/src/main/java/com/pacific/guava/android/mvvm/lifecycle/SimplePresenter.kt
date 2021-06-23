package com.pacific.guava.android.mvvm.lifecycle

import android.content.res.Configuration

/**
 * 生命周期回调函数融合了activity和fragment
 */
abstract class SimplePresenter : ViewLifecycle {

    override fun onCreate() {
    }

    override fun onCreateView() {
    }

    override fun onViewCreated() {
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroyView() {
    }

    override fun onDestroy() {
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
    }
}