package com.square.game.core.driver.instant

import com.square.game.core.base.*
import com.square.game.core.entities.GResult

object InstantDriver : NumberStateRunner {

    override fun applyStates(gResult: GResult) {
        val sum = gResult.numbers.sum()
        gResult.setState(
            sum,// 0-总和
            sum.largeSmallState(23),// 1-总和大小
            sum.oddEvenState(),// 2-总和单双
            dragonTigerState(gResult.numbers[0], gResult.numbers[4]),// 3-总和龙虎
            hashState(gResult.numbers),// 4-哈希
            hashStateFor3(gResult.numbers.left(3).sortedWith(ascComparatorInt)),// 5-前三
            hashStateFor3(gResult.numbers.range(1, 4).sortedWith(ascComparatorInt)),// 6-后三
            hashStateFor3(gResult.numbers.right(3).sortedWith(ascComparatorInt))// 7-后三
        )

        gResult.numbers.forEach {
            gResult.setState(
                it,// 号码
                it.largeSmallState(5),// 大小
                it.oddEvenState()// 单双
            )
        }
    }

    /**
     * 前中后三哈希
     */
    @JvmStatic
    fun hashStateFor3(sortedNumbers: List<Int>): Int {
        val a = sortedNumbers[0]
        val b = sortedNumbers[1]
        val c = sortedNumbers[2]
        if (a == b && a == c) {
            return SSS1// 豹子
        }
        if (
            (a + 1 == b && a + 2 == c) ||
            (a == 0 && b == 8 && c == 9) ||
            (a == 0 && b == 1 && c == 9)
        ) {
            return SSS2// 顺子
        }
        if (a == b || b == c) {
            return SSS3// 对子
        }
        if (a + 1 == b || b + 1 == c) {
            return SSS4// 半顺
        }
        return SSS5// 杂六
    }

    /**
     * 哈希
     */
    @JvmStatic
    fun hashState(sortedNumbers: IntArray): Int {
        val a = sortedNumbers[0]
        val b = sortedNumbers[1]
        val c = sortedNumbers[2]
        val d = sortedNumbers[3]
        val e = sortedNumbers[4]
        val distinct = sortedNumbers.distinct()

        if (a == e) {
            return SSS1// 五梅
        }
        if (a == d) {
            return SSS2// 炸弹
        }
        if (
            (a + 1 == b && a + 2 == c && a + 3 == d && a + 4 == e) ||
            (a == 0 && b == 6 && c == 7 && d == 8 && e == 9) ||
            (a == 0 && b == 1 && c == 7 && d == 8 && e == 9) ||
            (a == 0 && b == 1 && c == 2 && d == 8 && e == 9) ||
            (a == 0 && b == 1 && c == 2 && d == 3 && e == 9)
        ) {
            return SSS3// 顺子
        }
        if ((a == b && c == e) || (d == e && a == c)) {
            return SSS4// 葫芦
        }
        if (a == c || b == d || c == e) {
            return SSS5// 三条
        }
        if ((a == b && c == d) || (b == c && d == e) || (a == b && d == e)) {
            return SSS6// 两对
        }
        if (distinct.size == 5) {
            return SSS7// 五散
        }
        if (distinct.size == 4) {
            return SSS8// 单对
        }
        return SSS_UNKNOWN
    }
}