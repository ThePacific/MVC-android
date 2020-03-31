package com.pacific.core

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.pacific.core.dagger.CoreComponent
import com.pacific.core.dagger.DaggerCoreComponent
import com.pacific.core.initializer.enableStrictMode
import com.pacific.core.initializer.loadValues

const val APK_PACKAGE_ARCHIVE_TYPE = "application/vnd.android.package-archive"
const val SQL_DB3 = "sql.db3"
const val FILE_PREFS = "prefs"
const val ASSETS = "file:///android_asset/"

const val PREFS_DEVICE_ID = "deviceId"
const val PREFS_TOKEN1 = "token1"
const val PREFS_TOKEN2 = "token2"
const val PREFS_TOKEN3 = "token3"
const val PREFS_LOGIN_NAME = "loginName"
const val PREFS_LOGIN_PASSWORD = "loginPassword"

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
    appProcessName: String
) {
    contextApp = app
    loadValues(app, isDebug, appProcessName)
    coreComponent = DaggerCoreComponent.factory().create(app)
    coreComponent.provideAppInitializerManager().initialize(app)
    AppManager.initialize(app)

    if (isDebug) {
        enableStrictMode()
    }
}