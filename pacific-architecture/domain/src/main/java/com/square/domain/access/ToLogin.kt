package com.square.domain.access

import com.square.guava.domain.Source
import javax.inject.Inject

class ToLogin @Inject constructor(
    private val accessRepository: AccessRepository
) {
    suspend operator fun invoke(loginName: String, loginPassword: String): Source<String> {
        return accessRepository.login(loginName, loginPassword)
    }
}