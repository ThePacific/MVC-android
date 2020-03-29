package com.square.domain.access

import javax.inject.Inject

class ToTrial @Inject constructor(
    private val accessRepository: AccessRepository
) {
}