package com.pacific.example.common

import com.pacific.arch.example.BuildConfig

@JvmField
var DEBUG_APP: Boolean = BuildConfig.DEBUG

const val OS_PREFS = "os_prefs"
const val APK_EXT = ".apk"
const val DEFAULT_VERSION = "0.0.0"
const val APK_TYPE = "application/vnd.android.package-archive"

const val RELEASE_BASE_URL = "https://www.google.com/"
const val DEBUG_BASE_URL = "http://www.google.com/"