package com.square.domain.access

import javax.inject.Inject

class ToLogout @Inject constructor(
    private val accessRepository: AccessRepository
) {
}