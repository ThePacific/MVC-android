package com.square.core.initializer

import android.app.Application
import android.os.StrictMode
import com.square.core.storage.prefs.PrefsManager
import com.square.guava.domain.CoreJdk
import com.tencent.mmkv.MMKV

internal fun enableStrictMode() {
    StrictMode.setThreadPolicy(
        StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .penaltyLog()
            // .penaltyDeath()
            .build()
    )
    StrictMode.setVmPolicy(
        StrictMode.VmPolicy.Builder()
            .detectAll()
            .penaltyLog()
            // .penaltyDeath()
            .build()
    )
}

internal fun loadCoreJdk(app: Application, isDebug: Boolean, appProcessName: String) {
    MMKV.initialize(app)

    CoreJdk.isDebug = isDebug
    CoreJdk.appProcessName = appProcessName
    CoreJdk.baseUrl = "https://www.google.com/"
    CoreJdk.token1 = PrefsManager.getToken1()
    CoreJdk.token2 = PrefsManager.getToken2()
    CoreJdk.token3 = PrefsManager.getToken3()
    CoreJdk.token4 = PrefsManager.getToken4()
    CoreJdk.userId = 0L
    CoreJdk.loginName = PrefsManager.getLoginName()
    CoreJdk.loginPassword = PrefsManager.getLoginPassword()
    try {
        CoreJdk.deviceId = PrefsManager.getDeviceId()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}