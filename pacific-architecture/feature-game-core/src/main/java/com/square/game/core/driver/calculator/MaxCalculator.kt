package com.square.game.core.driver.calculator

import com.square.game.core.base.ascComparatorDouble
import com.square.game.core.entities.GNumber
import com.square.guava.math.MathUtils
import kotlin.math.min

/**
 * 一个号码一注，最大可中将注数大于1，当最大可中将数目为1时，所有行为保持和SimpleCalculator一致
 */
object MaxCalculator : Calculator {

    override fun calculate(vararg numbersArray: List<GNumber>): DoubleArray {
        require(numbersArray.size == 1)
        val numbers = numbersArray[0]
        if (numbers.isEmpty()) {
            return emptyCalculateResult
        }

        val trueMaxList = ArrayList<Double>()
        val trueMinList = ArrayList<Double>()
        val totalAmountList = ArrayList<Double>()
        var result: DoubleArray
        numbers.forEach {
            result = SimpleCalculator.calculate(listOf(it))
            trueMaxList.add(result[0])
            trueMinList.add(result[1])
            totalAmountList.add(result[4])
        }

        val nnn = nnn(*numbersArray)
        val length = min(nnn[0], trueMaxList.size)

        val trueMax = sumOfDoubleRight(trueMaxList.sortedWith(ascComparatorDouble), length)
        val trueMin = trueMinList.sortedWith(ascComparatorDouble).first()
        val totalAmount = sumOfDoubleLeft(totalAmountList)

        return doubleArrayOf(
            MathUtils.roundDownDouble(trueMax),
            MathUtils.roundDownDouble(trueMin),
            MathUtils.roundDownDouble(MathUtils.subtract(trueMax, totalAmount)),
            MathUtils.roundDownDouble(MathUtils.subtract(trueMin, totalAmount)),
            MathUtils.roundDownDouble(totalAmount)
        )
    }

    override fun betCount(vararg numbersArray: List<GNumber>): Int {
        return SimpleCalculator.betCount(*numbersArray)
    }

    /**
     * 最多可中奖注数
     */
    override fun nnn(vararg numbersArray: List<GNumber>): IntArray {
        require(numbersArray.size == 1)
        val numbers = numbersArray[0]
        if (numbers.isEmpty()) {
            return intArrayOf(Int.MAX_VALUE)
        }
        // -100, -200, -300 for test
        return when (val playId = numbers[0].playId) {
            -100L -> intArrayOf(1)
            -200L -> intArrayOf(2)
            -300L -> intArrayOf(3)
            else -> nnnOfMax(playId, numbers)
        }
    }
}