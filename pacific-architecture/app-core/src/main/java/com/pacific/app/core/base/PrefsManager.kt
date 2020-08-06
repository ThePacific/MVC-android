package com.pacific.app.core.base

import com.pacific.app.core.myApp
import com.pacific.app.data.file.AppPrefsManager
import com.pacific.guava.android.log.uniqueId
import com.tencent.mmkv.MMKV
import timber.log.Timber
import java.util.*

object PrefsManager : AppPrefsManager {

    private const val PREFS_DEVICE_ID = "deviceId"
    private const val PREFS_USER_ID = "userId"
    private const val PREFS_TOKEN1 = "token1"
    private const val PREFS_TOKEN2 = "token2"
    private const val PREFS_TOKEN3 = "token3"
    private const val PREFS_LOGIN_NAME = "loginName"
    private const val PREFS_LOGIN_PASSWORD = "loginPassword"
    private const val PREFS_SOFT_KEYBOARD_HEIGHT = "softKeyboardHeight"
    private const val PREFS_FLAVOR_ID = "flavorId"

    private val prefs: MMKV by lazy {
        MMKV.initialize(myApp)
        return@lazy MMKV.defaultMMKV()
    }

    override fun getUserId(): Long {
        return prefs.decodeLong(PREFS_USER_ID, 0L)
    }

    override fun setUserId(userId: Long): Boolean {
        return prefs.encode(PREFS_USER_ID, userId)
    }

    override fun getDeviceId(): String {
        var deviceId = prefs.decodeString(PREFS_DEVICE_ID, "")
        if (deviceId.isEmpty()) {
            deviceId = try {
                uniqueId(myApp)
            } catch (ignored: Exception) {
                Timber.tag("getDeviceId()").d(ignored)
                UUID.randomUUID().toString()
            }
            prefs.encode(PREFS_DEVICE_ID, deviceId)
        }
        return deviceId
    }

    override fun getToken1(): String {
        return prefs.decodeString(PREFS_TOKEN1, "")
    }

    override fun setToken1(token: String): Boolean {
        return prefs.encode(PREFS_TOKEN1, token)
    }

    override fun getToken2(): String {
        return prefs.decodeString(PREFS_TOKEN2, "")
    }

    override fun setToken2(token2: String): Boolean {
        return prefs.encode(PREFS_TOKEN2, token2)
    }

    override fun getToken3(): String {
        return prefs.decodeString(PREFS_TOKEN3, "")
    }

    override fun setToken3(token3: String): Boolean {
        return prefs.encode(PREFS_TOKEN3, token3)
    }

    override fun getLoginName(): String {
        return prefs.decodeString(PREFS_LOGIN_NAME, "")
    }

    override fun setLoginName(loginName: String): Boolean {
        return prefs.encode(PREFS_LOGIN_NAME, loginName)
    }

    override fun getLoginPassword(): String {
        return prefs.decodeString(PREFS_LOGIN_PASSWORD, "")
    }

    override fun setLoginPassword(loginPassword: String): Boolean {
        return prefs.encode(PREFS_LOGIN_PASSWORD, loginPassword)
    }

    override fun getSoftKeyboardHeight(): Int {
        return prefs.decodeInt(PREFS_SOFT_KEYBOARD_HEIGHT, 0)
    }

    override fun setSoftKeyboardHeight(softKeyboardHeight: Int): Boolean {
        return prefs.encode(PREFS_SOFT_KEYBOARD_HEIGHT, softKeyboardHeight)
    }

    override fun getFlavorId(): Int {
        return prefs.decodeInt(PREFS_FLAVOR_ID, 0)
    }

    override fun setFlavorId(flavorId: Int): Boolean {
        return prefs.encode(PREFS_FLAVOR_ID, flavorId)
    }
}