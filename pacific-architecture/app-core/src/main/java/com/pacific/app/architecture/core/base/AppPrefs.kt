package com.pacific.app.architecture.core.base

import com.pacific.app.architecture.store.file.PlatformPrefs
import com.pacific.guava.android.mvvm.AppOAuth2Prefs

object AppPrefs : AppOAuth2Prefs(), PlatformPrefs {

    override var softKeyboardHeight: Int
        get() = dataStore.decodeInt("softKeyboardHeight", 0)
        set(value) {
            dataStore.encode("softKeyboardHeight", value)
        }

    fun verifyToken(): Boolean = token.length >= 16
}