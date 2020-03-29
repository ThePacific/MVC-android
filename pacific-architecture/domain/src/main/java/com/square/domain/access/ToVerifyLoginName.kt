package com.square.domain.access

import javax.inject.Inject

class ToVerifyLoginName @Inject constructor(
    private val accessRepository: AccessRepository
) {
}