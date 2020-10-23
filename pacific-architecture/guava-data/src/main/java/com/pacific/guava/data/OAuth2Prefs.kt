package com.pacific.guava.data

interface OAuth2Prefs {

    var loginName: String

    var loginPassword: String

    var userId: String

    var token: String

    var flavorId: Int

    val deviceId: String
}