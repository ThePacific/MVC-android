package com.pacific.core.storage.prefs

import com.pacific.guava.android.log.uniqueId
import com.pacific.guava.domain.Values
import com.pacific.core.*
import com.tencent.mmkv.MMKV

object PrefsManager {

    private val mmvk: MMKV by lazy { MMKV.defaultMMKV() }

    fun getDeviceId(): String {
        var value = mmvk.decodeString(PREFS_DEVICE_ID, "")
        if (value.isEmpty()) {
            value = try {
                uniqueId(contextApp)
            } catch (ignored: Exception) {
                ""
            }
            setDeviceId(value)
        }
        return value
    }

    private fun setDeviceId(deviceId: String): Boolean {
        Values.deviceId = deviceId
        return mmvk.encode(PREFS_DEVICE_ID, deviceId)
    }

    fun getToken1(): String = mmvk.decodeString(PREFS_TOKEN1, "")

    fun setToken1(token: String): Boolean {
        Values.token1 = token
        return mmvk.encode(PREFS_TOKEN1, token)
    }

    fun getToken2(): String = mmvk.decodeString(PREFS_TOKEN2, "")

    fun setToken2(token: String): Boolean {
        Values.token2 = token
        return mmvk.encode(PREFS_TOKEN2, token)
    }

    fun getToken3(): String = mmvk.decodeString(PREFS_TOKEN3, "")

    fun setToken3(token: String): Boolean {
        Values.token3 = token
        return mmvk.encode(PREFS_TOKEN3, token)
    }

    fun getLoginName(): String = mmvk.decodeString(PREFS_LOGIN_NAME, "")

    fun setLoginName(loginName: String): Boolean {
        Values.loginName = loginName
        return mmvk.encode(PREFS_LOGIN_NAME, loginName)
    }

    fun getLoginPassword(): String = mmvk.decodeString(PREFS_LOGIN_PASSWORD, "")

    fun setLoginPassword(loginPassword: String): Boolean {
        Values.loginPassword = loginPassword
        return mmvk.encode(PREFS_LOGIN_PASSWORD, loginPassword)
    }
}