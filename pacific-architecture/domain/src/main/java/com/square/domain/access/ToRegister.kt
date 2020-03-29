package com.square.domain.access

import javax.inject.Inject

class ToRegister @Inject constructor(
    private val accessRepository: AccessRepository
) {
}