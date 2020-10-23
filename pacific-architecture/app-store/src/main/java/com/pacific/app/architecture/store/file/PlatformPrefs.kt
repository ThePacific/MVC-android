package com.pacific.app.architecture.store.file

import com.pacific.guava.data.OAuth2Prefs

interface PlatformPrefs : OAuth2Prefs {

    var softKeyboardHeight: Int
}