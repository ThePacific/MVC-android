package com.square.domain.user

import com.square.guava.domain.Source

interface UserRepository {

    suspend fun getUser(accessToken: String): Source<User>

    suspend fun getAccount(): Source<Account>
}