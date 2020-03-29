package com.square.game.core.driver.calculator

import com.square.guava.math.MathUtils
import com.square.game.core.entities.GNumber
import com.square.guava.domain.unsupportedOperationException

/**
 * 一个号码一注，有且仅有一个号码中奖
 */
object SimpleCalculator : Calculator {

    override fun calculate(vararg numbersArray: List<GNumber>): DoubleArray {
        require(numbersArray.size == 1)
        val numbers = numbersArray[0]
        if (numbers.isEmpty()) {
            return emptyCalculateResult
        }

        var trueMax = 0.000000
        var trueMin = 1000000.00
        var totalAmount = 0.000000
        var tempTrue: Double
        var factor: Double
        numbers.forEach {
            factor = it.factor.toDouble()
            tempTrue = MathUtils.multiply(it.odds1, factor)
            trueMax = maxOf(trueMax,tempTrue)
            trueMin = minOf(trueMin,tempTrue)
            totalAmount = MathUtils.plus(totalAmount, MathUtils.multiply(factor, it.amount))
        }

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
        return numbersArray[0].size
    }

    override fun nnn(vararg numbersArray: List<GNumber>): IntArray {
        return unsupportedOperationException()
    }
}