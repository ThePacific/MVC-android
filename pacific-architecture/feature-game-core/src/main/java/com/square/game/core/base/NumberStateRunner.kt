package com.square.game.core.base

import com.square.game.core.entities.GResult

interface NumberStateRunner {

    fun applyStates(gResult: GResult)
}