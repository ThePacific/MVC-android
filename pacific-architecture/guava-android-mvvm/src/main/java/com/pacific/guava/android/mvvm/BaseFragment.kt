package com.pacific.guava.android.mvvm

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.pacific.guava.jvm.coroutines.Bus
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment(@LayoutRes contentLayoutId: Int = 0) : Fragment(contentLayoutId) {

    var onResumeCount = 0
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Bus.subscribe()
            .onEach { pair ->
                when (pair.first) {
                    AndroidX.BUS_LOGIN -> onLogin()
                    AndroidX.BUS_LOGOUT -> onLogout()
                    else -> onBusEvent(pair)
                }
            }
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

    open fun onBackPressed(): Boolean = false

    open fun onMoveToForeground() {}

    open fun onMoveToBackground() {}

    open fun onLogin() {}

    open fun onLogout() {}

    open fun onBusEvent(event: Pair<Int, Any>) {}
}