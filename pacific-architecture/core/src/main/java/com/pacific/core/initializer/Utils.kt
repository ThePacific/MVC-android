package com.pacific.core.initializer

import android.app.Application
import android.os.StrictMode
import com.pacific.core.storage.prefs.PrefsManager
import com.pacific.guava.GOOGLE
import com.pacific.guava.domain.JdkTimber
import com.pacific.guava.domain.Values
import com.pacific.guava.jdkTimber
import com.tencent.mmkv.MMKV
import timber.log.Timber

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

internal fun loadValues(app: Application, isDebug: Boolean, appProcessName: String) {
    MMKV.initialize(app)

    Values.isDebug = isDebug
    Values.appProcessName = appProcessName
    Values.apiUrl1 = GOOGLE
    Values.apiUrl2 = GOOGLE
    Values.apiUrl3 = GOOGLE
    Values.token1 = PrefsManager.getToken1()
    Values.token2 = PrefsManager.getToken2()
    Values.token3 = PrefsManager.getToken3()
    Values.userId = 0L
    Values.loginName = PrefsManager.getLoginName()
    Values.loginPassword = PrefsManager.getLoginPassword()
    try {
        Values.deviceId = PrefsManager.getDeviceId()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    jdkTimber = object : JdkTimber {

        override fun d(message: String, vararg args: String) {
            Timber.d(message, args)
        }
    }
}