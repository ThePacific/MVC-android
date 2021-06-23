package com.pacific.guava.android.mvvm

import android.app.Application
import com.pacific.guava.jvm.Guava
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

object AndroidX {

    const val APK_PACKAGE_ARCHIVE_TYPE = "application/vnd.android.package-archive"// apk类型
    const val ASSETS = "file:///android_asset/"// assert路径

    val dialogCount: MutableStateFlow<Int> = MutableStateFlow(0)// 当前app对话框数量
    val isNetworkConnected: MutableStateFlow<Boolean> = MutableStateFlow(false)// 是否有网络
    val isAppInForeground: MutableStateFlow<Boolean> = MutableStateFlow(false)// 是否前后台
    val exitApp: MutableStateFlow<Boolean> = MutableStateFlow(false)// 退出app

    lateinit var myApp: Application
        // 上下文
        private set

    /**
     * 初始化AndroidX模块
     */
    fun setup(app: Application, isDebug: Boolean) {
        if (::myApp.isInitialized) {
            return
        }

        myApp = app
        Guava.isDebug = isDebug// 是否debug
        Guava.timber = AppTimber()// 调试log
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
     * 推出app
     */
    fun exitSystem() {
        exitApp.value = true
        android.os.Process.killProcess(android.os.Process.myPid())
    }

    /**
     * dialog数量加
     */
    fun notifyDialogShow() {
        dialogCount.value = dialogCount.value + 1
    }

    /**
     * dialog数量减
     */
    fun notifyDialogDismiss() {
        dialogCount.value = dialogCount.value - 1
    }
}