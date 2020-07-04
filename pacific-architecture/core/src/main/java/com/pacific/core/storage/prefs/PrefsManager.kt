package com.pacific.core.storage.prefs

import com.pacific.core.*
import com.pacific.data.files.AppPrefsManager
import com.pacific.guava.android.log.uniqueId
import com.tencent.mmkv.MMKV
import timber.log.Timber
import java.util.*

object PrefsManager : AppPrefsManager {

    private val mmvk: MMKV by lazy { MMKV.defaultMMKV() }

    override fun getUserId(): Long {
        return mmvk.decodeLong(PREFS_USER_ID, 0L)
    }

    override fun setUserId(userId: Long): Boolean {
        return mmvk.encode(PREFS_USER_ID, userId)
    }

    override fun getDeviceId(): String {
        var deviceId = mmvk.decodeString(PREFS_DEVICE_ID, "")
        if (deviceId.isEmpty()) {
            deviceId = try {
                uniqueId(contextApp)
            } catch (ignored: Exception) {
                Timber.e(ignored)
                UUID.randomUUID().toString()
            }
            mmvk.encode(PREFS_DEVICE_ID, deviceId)
        }
        return deviceId
    }

    override fun getToken1(): String {
        return mmvk.decodeString(PREFS_TOKEN1, "")
    }

    override fun setToken1(token: String): Boolean {
        return mmvk.encode(PREFS_TOKEN1, token)
    }

    override fun getToken2(): String {
        return mmvk.decodeString(PREFS_TOKEN2, "")
    }

    override fun setToken2(token2: String): Boolean {
        return mmvk.encode(PREFS_TOKEN2, token2)
    }

    override fun getToken3(): String {
        return mmvk.decodeString(PREFS_TOKEN3, "")
    }

    override fun setToken3(token3: String): Boolean {
        return mmvk.encode(PREFS_TOKEN3, token3)
    }

    override fun getLoginName(): String {
        return mmvk.decodeString(PREFS_LOGIN_NAME, "")
    }

    override fun setLoginName(loginName: String): Boolean {
        return mmvk.encode(PREFS_LOGIN_NAME, loginName)
    }

    override fun getLoginPassword(): String {
        return mmvk.decodeString(PREFS_LOGIN_PASSWORD, "")
    }

    override fun setLoginPassword(loginPassword: String): Boolean {
        return mmvk.encode(PREFS_LOGIN_PASSWORD, loginPassword)
    }

    override fun getSoftKeyboardHeight(): Int {
        return mmvk.decodeInt(PREFS_SOFT_KEYBOARD_HEIGHT, 0)
    }

    override fun setSoftKeyboardHeight(softKeyboardHeight: Int): Boolean {
        return mmvk.encode(PREFS_SOFT_KEYBOARD_HEIGHT, softKeyboardHeight)
    }

    override fun getFlavorId(): Int {
        return mmvk.decodeInt(PREFS_FLAVOR_ID, 0)
    }

    override fun setFlavorId(flavorId: Int): Boolean {
        return mmvk.encode(PREFS_FLAVOR_ID, flavorId)
    }
}