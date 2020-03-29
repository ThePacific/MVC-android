package com.square.game.core.driver.happy10

import com.square.game.core.base.*
import com.square.game.core.entities.GResult

object Happy10Driver : NumberStateRunner {

    override fun applyStates(gResult: GResult) {
        val sum = gResult.numbers.sum()
        gResult.setState(
            sum,// 总和
            sum.largeSmallState2(84),// 总和大小
            sum.oddEvenState(),// 总和单双
            sum.ofEndN().largeSmallState(5)// 总和尾大尾小
        )

        var number: Int
        for (i in gResult.numbers.indices) {
            number = gResult.numbers[i]
            gResult.setState(
                number,// 号码
                number.largeSmallState(11),// 大小
                number.oddEvenState(),// 单双
                number.ofUnitSum().oddEvenState(),// 合单合双
                number.ofEndN().largeSmallState(5),// 尾大尾小
                directionState(number),// 东南西北
                hashState(number),// 中发白
                gResult.numbers.dragonTigerState(i)// 龙虎
            )
        }
    }

    /**
     * 东南西北
     */
    @JvmStatic
    fun directionState(number: Int): Int {
        return when {
            eastNumbers.contains(number) -> SSS1
            southNumbers.contains(number) -> SSS2
            westNumbers.contains(number) -> SSS3
            northNumbers.contains(number) -> SSS4
            else -> SSS_UNKNOWN
        }
    }

    /**
     * 中发白
     */
    @JvmStatic
    fun hashState(number: Int): Int {
        return when {
            middleNumbers.contains(number) -> SSS1
            exposeNumbers.contains(number) -> SSS2
            blankNumbers.contains(number) -> SSS3
            else -> SSS_UNKNOWN
        }
    }
}