package com.square.core.storage.prefs

import com.square.core.myApp
import com.square.core.storage.*
import com.square.guava.android.log.uniqueId
import com.square.guava.domain.CoreJdk
import com.tencent.mmkv.MMKV

object PrefsManager {

    private val mmvk: MMKV by lazy {
        MMKV.defaultMMKV()
    }

    fun getDeviceId(): String {
        var value = mmvk.decodeString(PREFS_DEVICE_ID, "")
        if (value.isEmpty()) {
            value = try {
                uniqueId(myApp)
            } catch (ignored: Exception) {
                ""
            }
            setDeviceId(value)
        }
        return value
    }

    private fun setDeviceId(deviceId: String): Boolean {
        CoreJdk.deviceId = deviceId
        return mmvk.encode(PREFS_DEVICE_ID, deviceId)
    }

    fun getToken1(): String = mmvk.decodeString(PREFS_TOKEN1, "")

    fun setToken1(token: String): Boolean {
        CoreJdk.token1 = token
        return mmvk.encode(PREFS_TOKEN1, token)
    }

    fun getToken2(): String = mmvk.decodeString(PREFS_TOKEN2, "")

    fun setToken2(token: String): Boolean {
        CoreJdk.token2 = token
        return mmvk.encode(PREFS_TOKEN2, token)
    }

    fun getToken3(): String = mmvk.decodeString(PREFS_TOKEN3, "")

    fun setToken3(token: String): Boolean {
        CoreJdk.token3 = token
        return mmvk.encode(PREFS_TOKEN3, token)
    }

    fun getToken4(): String = mmvk.decodeString(PREFS_TOKEN4, "")

    fun setToken4(token: String): Boolean {
        CoreJdk.token4 = token
        return mmvk.encode(PREFS_TOKEN4, token)
    }

    fun getLoginName(): String = mmvk.decodeString(PREFS_LOGIN_NAME, "")

    fun setLoginName(loginName: String): Boolean {
        CoreJdk.loginName = loginName
        return mmvk.encode(PREFS_LOGIN_NAME, loginName)
    }

    fun getLoginPassword(): String = mmvk.decodeString(PREFS_LOGIN_PASSWORD, "")

    fun setLoginPassword(loginPassword: String): Boolean {
        CoreJdk.loginPassword = loginPassword
        return mmvk.encode(PREFS_LOGIN_PASSWORD, loginPassword)
    }

    fun getSwitch1(): Boolean = mmvk.decodeBool(PREFS_SWITCH1, false)

    fun setSwitch1(switch1: Boolean): Boolean = mmvk.encode(PREFS_SWITCH1, switch1)

    fun getSwitch2(): Boolean = mmvk.decodeBool(PREFS_SWITCH2, false)

    fun setSwitch2(switch2: Boolean): Boolean = mmvk.encode(PREFS_SWITCH2, switch2)

    fun getSwitch3(): Boolean = mmvk.decodeBool(PREFS_SWITCH3, false)

    fun setSwitch3(switch3: Boolean): Boolean = mmvk.encode(PREFS_SWITCH3, switch3)

    fun getSwitch4(): Boolean = mmvk.decodeBool(PREFS_SWITCH4, false)

    fun setSwitch4(switch4: Boolean): Boolean = mmvk.encode(PREFS_SWITCH4, switch4)

    fun getSwitch5(): Boolean = mmvk.decodeBool(PREFS_SWITCH5, false)

    fun setSwitch5(switch5: Boolean): Boolean = mmvk.encode(PREFS_SWITCH5, switch5)

    fun getSwitch6(): Boolean = mmvk.decodeBool(PREFS_SWITCH6, false)

    fun setSwitch6(switch6: Boolean): Boolean = mmvk.encode(PREFS_SWITCH6, switch6)

    fun getSwitch7(): Boolean = mmvk.decodeBool(PREFS_SWITCH7, false)

    fun setSwitch7(switch7: Boolean): Boolean = mmvk.encode(PREFS_SWITCH7, switch7)

    fun getSwitch8(): Boolean = mmvk.decodeBool(PREFS_SWITCH8, false)

    fun setSwitch8(switch8: Boolean): Boolean = mmvk.encode(PREFS_SWITCH8, switch8)

    fun getSwitch9(): Boolean = mmvk.decodeBool(PREFS_SWITCH9, false)

    fun setSwitch9(switch9: Boolean): Boolean = mmvk.encode(PREFS_SWITCH9, switch9)
}