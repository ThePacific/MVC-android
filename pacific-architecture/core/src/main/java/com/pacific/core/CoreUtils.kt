package com.pacific.core

import android.app.Application
import android.os.StrictMode
import androidx.lifecycle.MutableLiveData
import com.pacific.core.dagger.CoreComponent
import com.pacific.core.dagger.DaggerCoreComponent
import com.pacific.core.initializer.AndroidTimber
import com.pacific.data.dataComponent
import com.pacific.guava.Guava

const val APK_PACKAGE_ARCHIVE_TYPE = "application/vnd.android.package-archive"
const val SQL_DB3 = "sql.db3"
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
const val BUS_DIALOG_COUNT = 901

@get:JvmName("myApp")
lateinit var myApp: Application
    private set

@get:JvmName("coreComponent")
lateinit var coreComponent: CoreComponent
    private set

@get:JvmName("appDialogCount")
val appDialogCount: MutableLiveData<Int> by lazy { MutableLiveData(0) }

val isNetworkConnected: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }

val isAppInForeground: MutableLiveData<Boolean> by lazy { MutableLiveData(false) }

fun setupCoreModule(
    app: Application,
    isDebug: Boolean,
    appComponent: CoreComponent = DaggerCoreComponent.factory().create(app)
) {
    Guava.isDebug = isDebug
    Guava.timber = AndroidTimber

    myApp = app
    coreComponent = appComponent
    dataComponent = appComponent

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