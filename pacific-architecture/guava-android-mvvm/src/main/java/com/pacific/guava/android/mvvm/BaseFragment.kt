package com.pacific.guava.android.mvvm

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.pacific.guava.jvm.coroutines.Bus
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Fragment基类
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    var onResumeCount = 0
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidX.isAppInForeground.asLiveData().observe(this, {
            if (it == true) {
                onMoveToForeground()
            } else {
                onMoveToBackground()
            }
        })

        AndroidX.isNetworkConnected.asLiveData().observe(this, {
            if (it == true) {
                onNetworkConnected()
            } else {
                onNetworkDisconnected()
            }
        })

        AndroidX.dialogCount.asLiveData().observe(this, {
            onDialogCount(it)
        })

        Bus.subscribe()
            .onEach { pair -> onBusEvent(pair) }
            .catch { e -> e.printStackTrace() }
            .launchIn(lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
    }

    override fun onResume() {
        super.onResume()
        onResumeCount++
    }

    open fun onMoveToForeground() {}

    open fun onMoveToBackground() {}

    open fun onNetworkConnected() {}

    open fun onNetworkDisconnected() {}

    open fun onDialogCount(count: Int) {}

    open fun onBusEvent(event: Pair<Int, Any>) {}
}