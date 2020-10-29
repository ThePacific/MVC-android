package com.pacific.guava.android.mvvm

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.pacific.guava.jvm.Guava
import com.pacific.guava.jvm.coroutines.Bus
import com.pacific.guava.jvm.domain.LibX
import timber.log.Timber

object AndroidX : LibX {

    const val APK_PACKAGE_ARCHIVE_TYPE = "application/vnd.android.package-archive"
    const val ASSETS = "file:///android_asset/"

    const val BUS_EXIT = 9000
    const val BUS_LOGOUT = 9001
    const val BUS_LOGIN = 9002
    const val BUS_DIALOG_COUNT = 9003
    const val BUS_DIALOG_CLOSE = 9004

    @get:JvmName("appDialogCount")
    val appDialogCount: MutableLiveData<Int> = MutableLiveData(0)

    @get:JvmName("isNetworkConnected")
    val isNetworkConnected: MutableLiveData<Boolean> = MutableLiveData()

    @get:JvmName("isAppInForeground")
    val isAppInForeground: MutableLiveData<Boolean> = MutableLiveData()

    @get:JvmName("myApp")
    lateinit var myApp: Application
        private set

    fun setup(app: Application, isDebug: Boolean) {
        if (isAlreadyInitialized) {
            return
        }

        myApp = app
        Guava.isDebug = isDebug
        Guava.timber = AppTimber()
        if (isDebug) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun exitSystem() = Bus.offer(BUS_EXIT)

    fun notifyLogin() = Bus.offer(BUS_LOGIN)

    fun notifyLogout() = Bus.offer(BUS_LOGOUT)

    fun notifyDialogShow() {
        appDialogCount.value = appDialogCount.value!! + 1
        Bus.offer(BUS_DIALOG_COUNT)
    }

    fun notifyDialogDismiss() {
        appDialogCount.value = appDialogCount.value!! - 1
        Bus.offer(BUS_DIALOG_COUNT)
    }

    override val isAlreadyInitialized: Boolean
        get() = ::myApp.isInitialized
}