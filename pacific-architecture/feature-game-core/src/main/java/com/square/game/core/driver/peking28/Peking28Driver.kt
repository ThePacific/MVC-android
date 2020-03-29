package com.square.game.core.driver.peking28

import com.square.game.core.base.*
import com.square.game.core.base.NumberStateRunner
import com.square.game.core.entities.GResult

object Peking28Driver : NumberStateRunner {

    override fun applyStates(gResult: GResult) {
        val sum = gResult.numbers.sum()
        val sumLargeSmallState = sum.largeSmallState(14)
        val sumOddEvenState = sum.oddEvenState()
        gResult.setState(
            sum,// 总和
            sumLargeSmallState,// 总和大小
            sumOddEvenState,// 总和单双
            sumDragonTigerState(gResult.numbers),// 总和龙虎
            sumColorState(sum),// 总和波色
            sumVeryState(sum),//  总和极大、极小
            hashState(gResult.sortedNumbers),// 哈希
            largeSmallOddEvenState(sumLargeSmallState, sumOddEvenState)// 总和大单、大双、小单、小双
        )

        gResult.numbers.forEach {
            gResult.setState(
                it, // 号码
                it.largeSmallState(5),// 大小
                it.oddEvenState()// 单双
            )
        }
    }

    /**
     * 总和龙虎
     */
    @JvmStatic
    fun sumDragonTigerState(numbers: IntArray): Int {
        val a = numbers[0]
        val b = numbers[2]
        return when {
            a > b -> SSS1
            a < b -> SSS2
            else -> SSS3
        }
    }

    /**
     * 哈希
     */
    @JvmStatic
    fun hashState(sortedNumbers: IntArray): Int {
        val a = sortedNumbers[0]
        val b = sortedNumbers[1]
        val c = sortedNumbers[2]
        return when {
            a == c -> SSS1// 豹子
            a + 1 == b && a + 2 == c -> SSS2// 顺子
            a == b || b == c -> SSS3// 对子
            else -> SSS_UNKNOWN
        }
    }

    /**
     * 总和波色
     */
    @JvmStatic
    fun sumColorState(sum: Int): Int {
        return when {
            redSums.contains(sum) -> SSS1
            blueSums.contains(sum) -> SSS2
            greenSums.contains(sum) -> SSS3
            else -> SSS_UNKNOWN
        }
    }

    /**
     * 总和极大、极小
     */
    @JvmStatic
    fun sumVeryState(sum: Int): Int {
        return when {
            sum >= 22 -> SSS1
            sum <= 5 -> SSS2
            else -> SSS_UNKNOWN
        }
    }
}