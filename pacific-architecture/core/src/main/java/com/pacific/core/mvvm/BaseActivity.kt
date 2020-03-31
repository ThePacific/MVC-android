package com.pacific.core.mvvm

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.lifecycleScope
import com.pacific.guava.android.context.hideSoftInput
import com.pacific.guava.coroutines.Bus
import com.pacific.core.BUS_EXIT_APP
import com.pacific.core.isAppInForeground
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseActivity(
    @LayoutRes contentLayoutId: Int = 0
) : AppCompatActivity(contentLayoutId) {

    private var postponedTransition = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isAppInForeground.observe(this) {
            if (it == true) {
                onMoveToForeground()
            } else {
                onMoveToBackground()
            }
        }

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

    override fun finishAfterTransition() {
        val resultData = Intent()
        val resultCode = onPopulateResultIntent(resultData)
        setResult(resultCode, resultData)

        super.finishAfterTransition()
    }

    fun scheduleStartPostponedTransitions() {
        if (postponedTransition) {
            this.window.decorView.doOnPreDraw {
                startPostponedEnterTransition()
            }
        }
    }

    open fun onPopulateResultIntent(resultData: Intent): Int {
        return Activity.RESULT_OK
    }

    open fun handleIntent(intent: Intent) {}

    open fun onMoveToForeground() {}

    open fun onMoveToBackground() {}

    open fun onBusEvent(event: Pair<Int, Any>) {}
}