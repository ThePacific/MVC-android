package com.square.game.core.driver.rapid3

import com.square.game.core.base.*
import com.square.game.core.entities.GResult

object Rapid3Driver : NumberStateRunner {

    override fun applyStates(gResult: GResult) {
        val sum = gResult.numbers.sum()
        val sumLargeSmallState = sum.largeSmallState(10)
        val sumOddEvenState = sum.oddEvenState()
        gResult.setState(
            sum,// 总和
            sumLargeSmallState,// 总和大小
            sumOddEvenState,// 总和单双
            largeSmallOddEvenState(sumLargeSmallState, sumOddEvenState),// 总和大单、大双、小单、小双
            same2Number1(gResult.sortedNumbers),// 二同号单选1-号码
            same2Number2(gResult.sortedNumbers),// 二同号单选2-号码
            same2Number3(gResult.sortedNumbers),// 二同号复选-号码
            same3Number(gResult.sortedNumbers),// 三同号-号码
            same3State(gResult.sortedNumbers),// 三同号复选
            diff3State1(gResult.sortedNumbers),// 三不同号
            diff3State2(gResult.sortedNumbers)// 三不同号，连号
        )
        gResult.setState(*gResult.numbers)// 号码
    }

    /**
     * 二同号单选1-号码
     */
    @JvmStatic
    fun same2Number1(sortedNumbers: IntArray): Int {
        if (sortedNumbers[0] == sortedNumbers[1] && sortedNumbers[0] != sortedNumbers[2]) {
            return sortedNumbers[1]
        }
        if (sortedNumbers[1] == sortedNumbers[2] && sortedNumbers[0] != sortedNumbers[2]) {
            return sortedNumbers[1]
        }
        return -1
    }

    /**
     * 二同号单选2-号码
     */
    @JvmStatic
    fun same2Number2(sortedNumbers: IntArray): Int {
        if (sortedNumbers[0] == sortedNumbers[1] && sortedNumbers[0] != sortedNumbers[2]) {
            return sortedNumbers[2]
        }
        if (sortedNumbers[1] == sortedNumbers[2] && sortedNumbers[0] != sortedNumbers[2]) {
            return sortedNumbers[0]
        }
        return -1
    }

    /**
     * 二同号复选-号码
     */
    @JvmStatic
    fun same2Number3(sortedNumbers: IntArray): Int {
        if (sortedNumbers[0] == sortedNumbers[1] || sortedNumbers[1] == sortedNumbers[2]) {
            return sortedNumbers[1]
        }
        return -1
    }

    /**
     * 三同号-号码
     */
    @JvmStatic
    fun same3Number(sortedNumbers: IntArray): Int {
        val a = sortedNumbers[0]
        val b = sortedNumbers[1]
        val c = sortedNumbers[2]
        if (a == b && b == c) {
            return a
        }
        return -1
    }

    /**
     * 三同号，复选
     */
    @JvmStatic
    fun same3State(sortedNumbers: IntArray): Int {
        if (same3Number(sortedNumbers) > 0) {
            return SSS1
        }
        return SSS2
    }

    /**
     * 三不同号
     */
    @JvmStatic
    fun diff3State1(sortedNumbers: IntArray): Int {
        val a = sortedNumbers[0]
        val b = sortedNumbers[1]
        val c = sortedNumbers[2]
        if (a != b && b != c && a != c) {
            return SSS1
        }
        return SSS2
    }

    /**
     * 三不同号，连号
     */
    @JvmStatic
    fun diff3State2(sortedNumbers: IntArray): Int {
        val a = sortedNumbers[0]
        val b = sortedNumbers[1]
        val c = sortedNumbers[2]
        if (a + 1 == b && a + 2 == c) {
            return SSS1
        }
        return SSS2
    }
}