package com.pacific.core

import android.app.Application
import android.os.StrictMode
import androidx.lifecycle.MutableLiveData
import com.pacific.core.dagger.CoreComponent
import com.pacific.core.dagger.DaggerCoreComponent
import com.pacific.core.storage.prefs.PrefsManager
import com.pacific.guava.Guava
import com.pacific.guava.domain.JdkTimber
import com.tencent.mmkv.MMKV
import timber.log.Timber

const val APK_PACKAGE_ARCHIVE_TYPE = "application/vnd.android.package-archive"
const val SQL_DB3 = "sql.db3"
const val FILE_PREFS = "prefs"
const val ASSETS = "file:///android_asset/"

const val PREFS_DEVICE_ID = "deviceId"
const val PREFS_USER_ID = "userId"
const val PREFS_TOKEN1 = "token1"
const val PREFS_TOKEN2 = "token2"
const val PREFS_TOKEN3 = "token3"
const val PREFS_LOGIN_NAME = "loginName"
const val PREFS_LOGIN_PASSWORD = "loginPassword"
const val PREFS_SOFT_KEYBOARD_HEIGHT = "softKeyboardHeight"
const val PREFS_FLAVOR_ID = "flavorId"

const val BUS_EXIT_APP = 900

lateinit var contextApp: Application
    private set

lateinit var coreComponent: CoreComponent
    private set

val isNetworkConnected: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }

val isMqttConnected: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }

val isSocketConnected: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }

val isWebSocketConnected: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }

val isAppInForeground: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }

fun isNetworkConnected(): Boolean = true == isNetworkConnected.value

fun isMqttConnected(): Boolean = true == isMqttConnected.value

fun isSocketConnected(): Boolean = true == isSocketConnected.value

fun isWebSocketConnected(): Boolean = true == isWebSocketConnected.value

fun isAppInForeground(): Boolean = true == isAppInForeground.value

fun setupCoreModule(
    app: Application,
    isDebug: Boolean,
    appComponent: CoreComponent = DaggerCoreComponent.factory().create(app, PrefsManager)
) {
    Guava.isDebug = isDebug
    Guava.timber = object : JdkTimber {

        override fun d(tag: String, message: String) {
            Timber.tag(tag).d(message)
        }

        override fun d(e: Throwable) {
            Timber.d(e)
        }

        override fun e(e: Throwable) {
            Timber.e(e)
        }

        override fun e(tag: String, message: String) {
            Timber.tag(tag).e(message)
        }
    }

    contextApp = app
    coreComponent = appComponent

    appComponent.appInitializerManager().initialize(app)
    AppManager.initialize(app)

    if (isDebug) {
        enableStrictMode()
    }
}

private fun enableStrictMode() {
    StrictMode.setThreadPolicy(
        StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build()
    )
    StrictMode.setVmPolicy(
        StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            .build()
    )
}