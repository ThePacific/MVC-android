package com.square.game.core.driver.calculator

import com.square.game.core.entities.GNumber
import com.square.guava.math.MathUtils

/**
 * 组合N*N*N计算方式 ，同时限制了最多可中奖注数
 */
object NNNCalculator : Calculator {

    override fun calculate(vararg numbersArray: List<GNumber>): DoubleArray {
        require(numbersArray.size > 1)
        val numbers = numbersArray[0]
        if (numbers.isEmpty()) {
            return emptyCalculateResult
        }

        val betCount = betCount(*numbersArray)
        val nnn = nnn(*numbersArray)
        val maxTrueCount = minOf(nnn[0], betCount)
        val factor = numbers[0].factor
        val amount = numbers[0].amount
        val odds = numbers[0].odds1
        val trueMax = MathUtils.multiply(odds, maxTrueCount * factor * amount)
        val trueMin = MathUtils.multiply(odds, (if (maxTrueCount > 0) 1 else 0) * factor * amount)
        val totalAmount = betCount * factor * amount
        return doubleArrayOf(
            MathUtils.roundDownDouble(trueMax),
            MathUtils.roundDownDouble(trueMin),
            MathUtils.roundDownDouble(trueMax - totalAmount),
            MathUtils.roundDownDouble(trueMin - totalAmount),
            MathUtils.roundDownDouble(totalAmount)
        )
    }

    override fun betCount(vararg numbersArray: List<GNumber>): Int {
        require(numbersArray.size > 1)
        var betCount = 1
        numbersArray.forEach {
            betCount *= it.size
        }
        return betCount
    }

    /**
     * 最多可中奖注数
     */
    override fun nnn(vararg numbersArray: List<GNumber>): IntArray {
        require(numbersArray.size > 1)
        val numbers = numbersArray[0]
        if (numbers.isEmpty()) {
            return intArrayOf(Int.MAX_VALUE)
        }
        // -100, -200, -300 for test
        // 只维护nnnOfNNN()
        return when (val playId = numbers[0].playId) {
            -100L -> intArrayOf(1)
            -200L -> intArrayOf(2)
            -300L -> intArrayOf(3)
            else -> nnnOfNNN(playId, *numbersArray)
        }
    }
}