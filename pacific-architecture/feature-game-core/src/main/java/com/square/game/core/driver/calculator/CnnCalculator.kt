package com.square.game.core.driver.calculator

import com.square.game.core.entities.GNumber
import com.square.guava.math.MathUtils

/**
 * 组合Cnn计算方式 ，同时限制了最多可中奖注数
 */
object CnnCalculator : Calculator {

    override fun calculate(vararg numbersArray: List<GNumber>): DoubleArray {
        require(numbersArray.size == 1)
        val numbers = numbersArray[0]
        if (numbers.isEmpty()) {
            return emptyCalculateResult
        }

        val nnn = nnn(*numbersArray)
        val betCount = betCount(*numbersArray)
        val maxTrueCount = minOf(betCount, nnn[1])
        val array = SimpleCalculator.calculate(listOf(resolveMaxOddsNumber(numbers)))
        val trueMax = array[0] * maxTrueCount
        val trueMin = array[1] * maxTrueCount
        val totalAmount = betCount * numbers[0].factor * numbers[0].amount
        return doubleArrayOf(
            MathUtils.roundDownDouble(trueMax),
            MathUtils.roundDownDouble(trueMin),
            MathUtils.roundDownDouble(MathUtils.subtract(trueMax, totalAmount)),
            MathUtils.roundDownDouble(MathUtils.subtract(trueMin, totalAmount)),
            MathUtils.roundDownDouble(totalAmount)
        )
    }

    override fun betCount(vararg numbersArray: List<GNumber>): Int {
        require(numbersArray.size == 1)
        val numbers = numbersArray[0]
        if (numbers.isEmpty()) {
            return 0
        }

        val size = numbers.size
        val nnn = nnn(*numbersArray)
        if (size < nnn[0]) {
            return 0
        }
        return MathUtils.combination(size, nnn[0])
    }

    /**
     * Cnm（n>m）[0]m的限定，且与n无关；[1]最多可中奖注数
     */
    override fun nnn(vararg numbersArray: List<GNumber>): IntArray {
        require(numbersArray.size == 1)
        val numbers = numbersArray[0]
        if (numbers.isEmpty()) {
            return intArrayOf(Int.MAX_VALUE, Int.MAX_VALUE)
        }
        // -100, -200, -300 for test
        // 只维护nnnOfCnn()
        return when (val playId = numbersArray[0][0].playId) {
            -100L -> intArrayOf(2, 1)
            -200L -> intArrayOf(3, 2)
            -300L -> intArrayOf(4, 3)
            else -> nnnOfCnn(playId, numbers)
        }
    }
}