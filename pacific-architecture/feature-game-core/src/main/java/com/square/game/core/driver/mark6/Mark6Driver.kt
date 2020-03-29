package com.square.game.core.driver.mark6

import com.square.game.core.base.*
import com.square.game.core.entities.GResult
import com.square.guava.domain.unsupportedOperationException

object Mark6Driver : NumberStateRunner {

    override fun applyStates(gResult: GResult) {
        var zodiac: Int
        var endN: Int
        var unitSum: Int

        gResult.numbers.forEach {
            zodiac = zodiacState(it, gResult.time)
            endN = it.ofEndN()
            unitSum = it.ofUnitSum()
            gResult.setState(
                    it,// 号码
                    it.largeSmallState2(25, 49),// 大小
                    it.oddEvenState(),// 单双
                    endN,// 尾数
                    endN.largeSmallState(5),// 尾大尾小
                    unitSum,// 合
                    unitSum.oddEvenState(),// 合单合双
                    nnState(it),// N~N
                    zodiac,// 生肖
                    beastHomeZodiacState(zodiac),// 野兽（家禽）
                    colorState(it)
            )
        }

        // 1-6龙虎
        gResult.setState(
                dragonTigerState2(gResult.numbers[0], gResult.numbers[1]),
                dragonTigerState2(gResult.numbers[0], gResult.numbers[2]),
                dragonTigerState2(gResult.numbers[0], gResult.numbers[3]),
                dragonTigerState2(gResult.numbers[0], gResult.numbers[4]),
                dragonTigerState2(gResult.numbers[0], gResult.numbers[5]),

                dragonTigerState2(gResult.numbers[1], gResult.numbers[2]),
                dragonTigerState2(gResult.numbers[1], gResult.numbers[3]),
                dragonTigerState2(gResult.numbers[1], gResult.numbers[4]),
                dragonTigerState2(gResult.numbers[1], gResult.numbers[5]),

                dragonTigerState2(gResult.numbers[2], gResult.numbers[3]),
                dragonTigerState2(gResult.numbers[2], gResult.numbers[4]),
                dragonTigerState2(gResult.numbers[2], gResult.numbers[5]),

                dragonTigerState2(gResult.numbers[3], gResult.numbers[4]),
                dragonTigerState2(gResult.numbers[3], gResult.numbers[5]),

                dragonTigerState2(gResult.numbers[4], gResult.numbers[5])
        )
    }

    /**
     * 号码生肖
     */
    @JvmStatic
    @JvmOverloads
    fun zodiacState(number: Int, time: Long, ignored: Int = -1): Int {
        if (number == ignored) {
            return -1
        }
        val zzzNumbers = if (time < zodiacsEpochMilli) {
            preZodiacNumbers
        } else {
            zodiacNumbers
        }
        zzzNumbers.forEach {
            if (it.value.contains(number)) {
                return it.key
            }
        }
        return ZODIAC_RAT
    }

    /**
     * 号码生肖
     */
    @JvmStatic
    @JvmOverloads
    fun zodiacState(number: Int, ignored: Int = -1): Int {
        if (number == ignored) {
            return -1
        }
        zodiacNumbers.forEach {
            if (it.value.contains(number)) {
                return it.key
            }
        }
        return ZODIAC_RAT
    }

    @JvmStatic
    fun zodiacStateName(zodiac: Int): String {
        return zodiacNames[zodiacs.indexOf(zodiac)]
    }

    /**
     * 号码波色
     */
    @JvmStatic
    fun colorState(number: Int): Int {
        return when {
            redNumbers.contains(number) -> SSS1
            blueNumbers.contains(number) -> SSS2
            greenNumbers.contains(number) -> SSS3
            else -> SSS1
        }
    }

    @JvmStatic
    fun colorStateName(colorState: Int): String {
        return when (colorState) {
            SSS1 -> "红"
            SSS2 -> "蓝"
            SSS3 -> "绿"
            else -> unsupportedOperationException()
        }
    }

    @JvmStatic
    fun beastHomeZodiacState(zodiac: Int): Int = if (beastZodiacs.contains(zodiac)) SSS1 else SSS2

    @JvmStatic
    fun havenGroundZodiacState(zodiac: Int): Int = if (havenZodiacs.contains(zodiac)) SSS1 else SSS2

    @JvmStatic
    fun starEndZodiacState(zodiac: Int): Int = if (startZodiacs.contains(zodiac)) SSS1 else SSS2

    @JvmStatic
    fun beastHomeZodiacStateName(state: Int): String {
        return when (state) {
            SSS1 -> "野肖"
            SSS2 -> "家肖"
            else -> "和"
        }
    }

    @JvmStatic
    fun havenGroundZodiacStateName(state: Int): String {
        return when (state) {
            SSS1 -> "天肖"
            SSS2 -> "地肖"
            else -> "和"
        }
    }

    @JvmStatic
    fun starEndZodiacStateName(state: Int): String {
        return when (state) {
            SSS1 -> "前肖"
            SSS2 -> "后肖"
            else -> "和"
        }
    }

    @JvmStatic
    fun zodiacCount(numbers: IntArray): Int {
        val zodiacs = IntArray(7) { zodiacState(numbers[it]) }
        return zodiacs.distinct().size
    }

    /**
     * N~N
     */
    @JvmStatic
    fun nnState(number: Int): Int {
        return when (number) {
            in 1..10 -> SSS1
            in 11..20 -> SSS2
            in 21..30 -> SSS3
            in 31..40 -> SSS4
            else -> SSS5
        }
    }
}