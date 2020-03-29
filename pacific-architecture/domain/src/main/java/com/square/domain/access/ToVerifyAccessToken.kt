package com.square.domain.access

import com.square.guava.domain.Source
import com.square.guava.extension.exhaustive
import javax.inject.Inject

class ToVerifyAccessToken @Inject constructor(
    private val accessRepository: AccessRepository
) {
    suspend operator fun invoke(accessToken: String): Boolean {
        if (accessToken.isNullOrEmpty()) return false
        return when (accessRepository.verifyAccessToken(accessToken)) {
            is Source.Error -> false
            is Source.Success -> true
        }.exhaustive
    }
}