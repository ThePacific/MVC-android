package com.pacific.guava.android.mvvm

import com.pacific.guava.android.log.getRandomUUID
import com.pacific.guava.android.log.uniqueId
import com.pacific.guava.data.OAuth2Prefs
import com.tencent.mmkv.MMKV
import com.tencent.mmkv.MMKVLogLevel
import timber.log.Timber

object AppOAuth2Prefs : OAuth2Prefs {

    private val dataStore: MMKV by lazy {
        MMKV.initialize(AndroidX.myApp, MMKVLogLevel.LevelNone)
        return@lazy MMKV.defaultMMKV()
    }

    override var loginName: String
        get() = dataStore.decodeString("loginName", "")
        set(value) {
            dataStore.encode("loginName", value)
        }

    override var loginPassword: String
        get() = dataStore.decodeString("loginPassword", "")
        set(value) {
            dataStore.encode("loginPassword", value)
        }

    override var userId: String
        get() = dataStore.decodeString("userId", "")
        set(value) {
            dataStore.encode("userId", value)
        }

    override var token: String
        get() = dataStore.decodeString("token", "")
        set(value) {
            dataStore.encode("token", value)
        }

    override var flavorId: Int
        get() = dataStore.decodeInt("flavorId", 0)
        set(value) {
            dataStore.encode("flavorId", value)
        }

    override val deviceId: String
        get() {
            var deviceId = dataStore.decodeString("deviceId", "")
            if (deviceId.isNullOrEmpty()) {
                deviceId = try {
                    uniqueId(AndroidX.myApp)
                } catch (ignored: Exception) {
                    Timber.d(ignored)
                    getRandomUUID()
                }
                dataStore.encode("deviceId", deviceId)
            }
            return deviceId
        }

    fun requireDataStore(): MMKV = dataStore

    fun verifyToken(): Boolean = token.length >= 16
}