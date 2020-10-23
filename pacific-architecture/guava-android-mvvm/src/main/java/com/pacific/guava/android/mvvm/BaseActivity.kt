package com.pacific.guava.android.mvvm

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.pacific.guava.android.context.hideSoftInput
import com.pacific.guava.jvm.coroutines.Bus
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseActivity(
    @LayoutRes contentLayoutId: Int = 0
) : AppCompatActivity(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidX.isAppInForeground.observe(this, {
            if (it == true) {
                onMoveToForeground()
            } else {
                onMoveToBackground()
            }
        })

        Bus.subscribe()
            .onEach { pair ->
                when (pair.first) {
                    AndroidX.BUS_EXIT -> finish()
                    AndroidX.BUS_LOGIN -> onLogin()
                    AndroidX.BUS_LOGOUT -> onLogout()
                    else -> onBusEvent(pair)
                }
            }
            .catch { e -> e.printStackTrace() }
            .launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.hideSoftInput()
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

    open fun onMoveToForeground() {}

    open fun onMoveToBackground() {}

    open fun onLogin() {}

    open fun onLogout() {}

    open fun onBusEvent(event: Pair<Int, Any>) {}
}