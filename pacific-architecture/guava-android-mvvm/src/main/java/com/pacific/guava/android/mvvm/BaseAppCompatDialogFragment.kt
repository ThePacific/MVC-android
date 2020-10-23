package com.pacific.guava.android.mvvm

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.lifecycle.lifecycleScope
import com.pacific.guava.jvm.coroutines.Bus
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseAppCompatDialogFragment : AppCompatDialogFragment() {

    var applyDialogCount = false
        protected set

    var onResumeCount = 0
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Bus.subscribe()
            .onEach { pair ->
                when (pair.first) {
                    AndroidX.BUS_LOGIN -> onLogin()
                    AndroidX.BUS_LOGOUT -> onLogout()
                    AndroidX.BUS_DIALOG_CLOSE -> dismissAllowingStateLoss()
                    else -> onBusEvent(pair)
                }
            }
            .catch { e -> e.printStackTrace() }
            .launchIn(lifecycleScope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
        if (applyDialogCount) {
            AndroidX.notifyDialogShow()
        }
    }

    override fun onResume() {
        super.onResume()
        onResumeCount++
    }

    override fun onDestroy() {
        super.onDestroy()
        if (applyDialogCount) {
            AndroidX.notifyDialogDismiss()
        }
    }

    open fun onBackPressed(): Boolean = false

    open fun onMoveToForeground() {}

    open fun onMoveToBackground() {}

    open fun onLogin() {}

    open fun onLogout() {}

    open fun onBusEvent(event: Pair<Int, Any>) {}
}