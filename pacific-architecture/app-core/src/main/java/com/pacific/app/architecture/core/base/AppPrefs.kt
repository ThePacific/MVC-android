package com.pacific.app.architecture.core.base

import com.pacific.app.architecture.store.file.PlatformPrefs
import com.pacific.guava.android.mvvm.AppOAuth2Prefs

object AppPrefs : PlatformPrefs {

    override var softKeyboardHeight: Int
        get() = AppOAuth2Prefs.requireDataStore().decodeInt("softKeyboardHeight", 0)
        set(value) {
            AppOAuth2Prefs.requireDataStore().encode("softKeyboardHeight", value)
        }

    override var loginName: String = AppOAuth2Prefs.loginName

    override var loginPassword: String = AppOAuth2Prefs.loginPassword

    override var userId: String = AppOAuth2Prefs.userId

    override var token: String = AppOAuth2Prefs.token

    override var flavorId: Int = AppOAuth2Prefs.flavorId

    override val deviceId: String = AppOAuth2Prefs.deviceId
}