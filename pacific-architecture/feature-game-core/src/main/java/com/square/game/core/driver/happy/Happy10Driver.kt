package com.square.game.core.driver.happy

import com.square.game.core.base.NumberStateRunner
import com.square.game.core.base.SSS1
import com.square.game.core.base.SSS2
import com.square.game.core.base.SSS3
import com.square.game.core.entities.GResult

object HappyDriver : NumberStateRunner {

    override fun applyStates(gResult: GResult) {
        val sum = gResult.numbers.sum()
    }

    /**
     * 前后多
     */
    @JvmStatic
    fun starEndCompareState(numbers: IntArray): Int {
        var startCount = 0
        var endCount = 0
        numbers.forEach {
            if (startNumbers.contains(it)) {
                startCount++
            } else {
                endCount++
            }
        }
        return when {
            startCount > endCount -> SSS1
            startCount < endCount -> SSS2
            else -> SSS3
        }
    }

    /**
     * 单双多
     */
    @JvmStatic
    fun oddEvenCompareState(numbers: IntArray): Int {
        var oddCount = 0
        var evenCount = 0
        numbers.forEach {
            if (it % 2 == 1) {
                oddCount++
            } else {
                evenCount++
            }
        }
        return when {
            oddCount > evenCount -> SSS1
            oddCount < evenCount -> SSS2
            else -> SSS3
        }
    }

    /**
     * 前后多
     */
    @JvmStatic
    fun starEndCompareStateName(state: Int): String {
        return when (state) {
            SSS1 -> "前多"
            SSS2 -> "后多"
            else -> "和"
        }
    }

    /**
     * 单双多
     */
    @JvmStatic
    fun oddEvenCompareStateName(state: Int): String {
        return when (state) {
            SSS1 -> "单多"
            SSS2 -> "双多"
            else -> "和"
        }
    }
}