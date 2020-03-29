package com.square.game.core.driver.calculator

import com.square.game.core.entities.GNumber

interface Calculator {
    /**
     * 最大可中奖、最小可中奖、最大可盈利、最小可盈利、总金额 DoubleArray[0,1,2,3,4]
     */
    fun calculate(vararg numbersArray: List<GNumber>): DoubleArray

    /**
     * 注数
     */
    fun betCount(vararg numbersArray: List<GNumber>): Int

    /**
     * 例如nnn，[0]和[1]是C[0][1]，[2]是最大可中奖数
     */
    fun nnn(vararg numbersArray: List<GNumber>): IntArray
}