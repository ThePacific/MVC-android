package com.square.core

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.square.core.dagger.CoreComponent
import com.square.core.dagger.DaggerCoreComponent
import com.square.core.initializer.enableStrictMode
import com.square.core.initializer.loadCoreJdk

const val APK_PACKAGE_ARCHIVE_TYPE = "application/vnd.android.package-archive"
const val SQL_DB3 = "sql.db3"
const val FILE_PREFS = "prefs"
const val ASSETS = "file:///android_asset/"

const val BUS_EXIT_APP = 900

lateinit var myApp: Application
    private set

lateinit var coreComponent: CoreComponent
    private set

val isNetworkConnected: MutableLiveData<Boolean> by lazy {
    MutableLiveData(false)
}

val isMqttConnected: MutableLiveData<Boolean> by lazy {
    MutableLiveData(false)
}

val isSocketConnected: MutableLiveData<Boolean> by lazy {
    MutableLiveData(false)
}

val isWebSocketConnected: MutableLiveData<Boolean> by lazy {
    MutableLiveData(false)
}

val isAppInForeground: MutableLiveData<Boolean> by lazy {
    MutableLiveData(false)
}

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
    myApp = app
    loadCoreJdk(app, isDebug, appProcessName)
    coreComponent = DaggerCoreComponent.factory().create(app)
    coreComponent.provideAppInitializerManager().initialize(app)
    AppManager.initialize(app)

    if (isDebug) {
        enableStrictMode()
    }
}