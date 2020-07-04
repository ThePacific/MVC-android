package com.pacific.core.mvvm

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.pacific.core.BUS_EXIT_APP
import com.pacific.core.isAppInForeground
import com.pacific.guava.android.context.hideSoftInput
import com.pacific.guava.coroutines.Bus
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseActivity(
        @LayoutRes contentLayoutId: Int = 0
) : AppCompatActivity(contentLayoutId) {

    private var postponedTransition = false

    val permissionsContract = ActivityResultContracts.RequestMultiplePermissions()
    val startActivityContract = ActivityResultContracts.StartActivityForResult()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAppInForeground.observe(
                this,
                Observer {
                    if (it == true) {
                        onMoveToForeground()
                    } else {
                        onMoveToBackground()
                    }
                }
        )

        Bus.subscribe()
                .onEach { pair -> if (pair.first == BUS_EXIT_APP) finish() else onBusEvent(pair) }
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

    override fun postponeEnterTransition() {
        super.postponeEnterTransition()
        postponedTransition = true
    }

    override fun startPostponedEnterTransition() {
        postponedTransition = false
        super.startPostponedEnterTransition()
    }

    fun scheduleStartPostponedTransitions() {
        if (postponedTransition) {
            this.window.decorView.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    open fun handleIntent(intent: Intent) {}

    open fun onMoveToForeground() {}

    open fun onMoveToBackground() {}

    open fun onBusEvent(event: Pair<Int, Any>) {}
}