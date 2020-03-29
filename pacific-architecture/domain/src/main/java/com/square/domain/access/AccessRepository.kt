package com.square.domain.access

import com.square.guava.domain.Source

interface AccessRepository {

    suspend fun verifyAccessToken(accessToken: String): Source<Boolean>

    suspend fun login(loginName: String, loginPassword: String): Source<String>

    suspend fun logout(token: String): Source<Boolean>

    suspend fun register(
        loginName: String,
        loginPassword: String,
        sisterCode: String,
        telephone: String,
        email: String,
        qq: String
    ): Source<Boolean>

    suspend fun verifyLoginName(loginName: String): Source<Boolean>
}