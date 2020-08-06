package com.pacific.app.data.file

interface AppPrefsManager {

    fun getUserId(): Long

    fun setUserId(userId: Long): Boolean

    fun getDeviceId(): String

    fun getToken1(): String

    fun setToken1(token: String): Boolean

    fun getToken2(): String

    fun setToken2(token2: String): Boolean

    fun getToken3(): String

    fun setToken3(token3: String): Boolean

    fun getLoginName(): String

    fun setLoginName(loginName: String): Boolean

    fun getLoginPassword(): String

    fun setLoginPassword(loginPassword: String): Boolean

    fun getSoftKeyboardHeight(): Int

    fun setSoftKeyboardHeight(softKeyboardHeight: Int): Boolean

    fun getFlavorId(): Int

    fun setFlavorId(flavorId: Int): Boolean
}