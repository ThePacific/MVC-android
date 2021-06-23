package com.pacific.guava.android.mvvm

import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.pacific.guava.android.context.hideSoftInput
import com.pacific.guava.jvm.coroutines.Bus
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * Activity基类
 */
abstract class BaseActivity(
    @LayoutRes contentLayoutId: Int = 0
) : AppCompatActivity(contentLayoutId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**监听app级别状态*/
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

        AndroidX.exitApp.asLiveData().observe(this, {
            if (it) {
                finish()
            }
        })

        Bus.subscribe()
            .onEach { pair -> onBusEvent(pair) }
            .catch { e -> e.printStackTrace() }
            .launchIn(lifecycleScope)
    }

    override fun onDestroy() {
        super.onDestroy()
        hideSoftInput()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        handleIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    // 重启
    open fun handleIntent(intent: Intent) {}

    // 转入前台
    open fun onMoveToForeground() {}

    // 转入后台
    open fun onMoveToBackground() {}

    // 网络已连接
    open fun onNetworkConnected() {}

    // 网络已断开
    open fun onNetworkDisconnected() {}

    // 对话框数量变更
    open fun onDialogCount(count: Int) {}

    // bug回调
    open fun onBusEvent(event: Pair<Int, Any>) {}
}