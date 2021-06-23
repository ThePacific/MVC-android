package com.pacific.guava.android.mvvm

import com.pacific.guava.android.log.uniqueId
import com.pacific.guava.data.OAuth2Prefs
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel

/**
 * 对android中对Prefs抽象实现，基本是每个app必备的字段
 */
abstract class AppOAuth2Prefs : OAuth2Prefs {

    // 登录名
    override var loginName: String
        get() = dataStore.decodeString("loginName").orEmpty()
        set(value) {
            dataStore.encode("loginName", value)
        }

    // 登录密码
    override var loginPassword: String
        get() = dataStore.decodeString("loginPassword").orEmpty()
        set(value) {
            dataStore.encode("loginPassword", value)
        }

    // 用户ID
    override var userId: String
        get() = dataStore.decodeString("userId").orEmpty()
        set(value) {
            dataStore.encode("userId", value)
        }

    // token
    override var token: String
        get() = dataStore.decodeString("token").orEmpty()
        set(value) {
            dataStore.encode("token", value)
        }

    // 渠道ID
    override var flavorId: Int
        get() = dataStore.decodeInt("flavorId", 0)
        set(value) {
            dataStore.encode("flavorId", value)
        }

    // 设备唯一id
    override val deviceId: String
        get() {
            var deviceId = dataStore.decodeString("deviceId").orEmpty()
            if (deviceId.isEmpty()) {
                deviceId = uniqueId(AndroidX.myApp)
                dataStore.encode("deviceId", deviceId)
            }
            return deviceId
        }

    companion object {

        @JvmStatic
        val dataStore: MMKV by lazy {// 使用腾讯开源MMKV库
            MMKV.initialize(AndroidX.myApp, MMKVLogLevel.LevelNone)
            return@lazy MMKV.defaultMMKV()!!
        }
    }
}