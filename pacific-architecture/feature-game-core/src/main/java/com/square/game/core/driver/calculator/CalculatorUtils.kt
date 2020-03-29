package com.square.game.core.driver.calculator

import com.square.game.core.entities.GNumber
import com.square.guava.math.MathUtils

val emptyCalculateResult: DoubleArray by lazy {
    doubleArrayOf(0.000000, 1000000.000000, 0.000000, 1000000.000000, 0.000000)
}

fun isEmptyCalculateResult(result: DoubleArray): Boolean {
    return result[0] == 0.000000 && result[2] == 0.000000 && result[4] == 0.000000
}

/**
 * 数组求和，顺序遍历
 */
@JvmOverloads
fun sumOfDoubleLeft(list: List<Double>, length: Int = list.size): Double {
    var sum = 0.000000
    for (i in 0 until length) {
        sum = MathUtils.plus(sum, list[i])
    }
    return sum
}

/**
 * 数组求和，倒叙遍历
 */
@JvmOverloads
fun sumOfDoubleRight(list: List<Double>, length: Int = list.size): Double {
    var sum = 0.000000
    for (i in list.size - 1 downTo list.size - length) {
        sum = MathUtils.plus(sum, list[i])
    }
    return sum
}

fun resolveMaxOddsNumber(numbers: List<GNumber>): GNumber {
    var maxOdds = -1000000.000000
    var index = -1
    for (i in numbers.indices) {
        if (numbers[i].odds1 > maxOdds) {
            maxOdds = numbers[i].odds1
            index = i
        }
    }
    return numbers[index]
}

fun mergeCalculateResults(vararg results: DoubleArray): DoubleArray {
    var trueMax = 0.000000
    var trueMin = 1000000.000000
    var totalAmount = 0.000000
    results.forEach {
        if (isEmptyCalculateResult(it)) {
            return@forEach
        }
        trueMax = MathUtils.plus(trueMax, it[0])
        trueMin = minOf(trueMin, it[1])
        totalAmount = MathUtils.plus(totalAmount, it[4])
    }

    return doubleArrayOf(
        MathUtils.roundDownDouble(trueMax),
        MathUtils.roundDownDouble(trueMin),
        MathUtils.roundDownDouble(MathUtils.subtract(trueMax, totalAmount)),
        MathUtils.roundDownDouble(MathUtils.subtract(trueMin, totalAmount)),
        MathUtils.roundDownDouble(totalAmount)
    )
}

fun nnnOfCnn(playId: Long, numbers: List<GNumber>): IntArray {
    return when (playId) {
        -100L -> intArrayOf(1)
        else -> intArrayOf(Int.MAX_VALUE)
    }
}

fun nnnOfNNN(playId: Long, vararg numbersArray: List<GNumber>): IntArray {
    return when (playId) {
        -100L -> intArrayOf(1)
        else -> intArrayOf(Int.MAX_VALUE)
    }
}

fun nnnOfMax(playId: Long, numbers: List<GNumber>): IntArray {
    return when (playId) {
        -100L -> intArrayOf(1)
        else -> intArrayOf(Int.MAX_VALUE)
    }
}