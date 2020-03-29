package com.square.game.core.base

import kotlin.math.sqrt

/**
 * 质合数
 */
fun Int.primeCompositeState(): Int {
    if (this <= 0) {
        return SSS2
    }
    if (this <= 3) {
        return SSS1
    }
    val a = sqrt(this.toDouble()).toInt()
    for (i in 2..a) {
        if (this % i == 0) {
            return SSS2
        }
    }
    return SSS1
}

/**
 * 质合数
 */
fun Int.primeCompositeStateOfMath(): Int {
    if (this < 2) {
        return SSS_UNKNOWN
    }
    if (this == 2 || this == 3) {
        return SSS1
    } else {
        val a = sqrt(this.toDouble()).toInt()
        for (i in 2..a) {
            if (this % i == 0) {
                return SSS2
            }
        }
        return SSS1
    }
}


/**
 * 单双
 */
fun Int.oddEvenState(): Int {
    return if (this % 2 == 1) SSS1 else SSS2
}

/**
 * 单双，特定情况下忽略计算
 */
fun Int.oddEvenState(ignored: Int): Int {
    if (this == ignored) return SSS_UNKNOWN
    return if (this % 2 == 1) SSS1 else SSS2
}

/**
 * 大小
 */
fun Int.largeSmallState(inclusiveNNN: Int): Int {
    return if (this >= inclusiveNNN) SSS1 else SSS2
}

/**
 * 大小，特定情况下忽略计算
 */
fun Int.largeSmallState(inclusiveNNN: Int, ignored: Int): Int {
    if (this == ignored) return SSS_UNKNOWN
    return if (this >= inclusiveNNN) SSS1 else SSS2
}

/**
 * 大小和
 */
fun Int.largeSmallState2(inclusiveNNN: Int): Int {
    return when {
        this > inclusiveNNN -> SSS1
        this < inclusiveNNN -> SSS2
        else -> SSS3
    }
}

/**
 * 大小和，特定情况下忽略计算
 */
fun Int.largeSmallState2(inclusiveNNN: Int, ignored: Int): Int {
    if (this == ignored) return SSS_UNKNOWN
    return when {
        this > inclusiveNNN -> SSS1
        this < inclusiveNNN -> SSS2
        else -> SSS3
    }
}

/**
 * 龙虎
 */
fun dragonTigerState(first: Int, second: Int): Int = first.largeSmallState(second)

/**
 * 龙虎，特定情况下忽略计算
 */
fun dragonTigerState(first: Int, second: Int, ignored: Int): Int {
    return first.largeSmallState(second, ignored)
}

/**
 * 龙虎和
 */
fun dragonTigerState2(first: Int, second: Int): Int = first.largeSmallState2(second)

/**
 * 龙虎和，特定情况下忽略计算
 */
fun dragonTigerState2(first: Int, second: Int, ignored: Int): Int {
    return first.largeSmallState2(second, ignored)
}

/**
 * 大单、大双、小单、小双
 */
fun largeSmallOddEvenState(largeSmallState: Int, oddEvenState: Int): Int {
    if (largeSmallState <= SSS3 || oddEvenState <= SSS3) {
        return SSS_UNKNOWN
    }
    return if (largeSmallState == SSS1) {
        if (oddEvenState == SSS1) {
            SSS1// 大单
        } else {
            SSS2// 大双
        }
    } else {
        if (oddEvenState == SSS1) {
            SSS3// 小单
        } else {
            SSS4// 小双
        }
    }
}

/**
 * 单、双、和名字
 */
fun Int.oddEvenStateName(): String {
    return when (this) {
        SSS1 -> "单"
        SSS2 -> "双"
        else -> "和"
    }
}

/**
 * 大、小、和名字
 */
fun Int.largeSmallStateName(): String {
    return when (this) {
        SSS1 -> "大"
        SSS2 -> "小"
        else -> "和"
    }
}

/**
 * 龙、虎、和名字
 */
fun Int.dragonTigerStateName(): String {
    return when (this) {
        SSS1 -> "龙"
        SSS2 -> "虎"
        else -> "和"
    }
}

/**
 * 合单、双、和名字
 */
fun Int.unitSumOddEvenStateName(): String {
    return when (this) {
        SSS1 -> "合单"
        SSS2 -> "合双"
        else -> "和"
    }
}

/**
 * 尾大、小、和名字
 */
fun Int.unitSumLargeSmallStateName(): String {
    return when (this) {
        SSS1 -> "合大"
        SSS2 -> "合小"
        else -> "和"
    }
}

/**
 * 尾大、小、和名字
 */
fun Int.endNLargeSmallStateName(): String {
    return when (this) {
        SSS1 -> "尾大"
        SSS2 -> "尾小"
        else -> "和"
    }
}

/**
 * 尾单、双、和名字
 */
fun Int.endNOddEvenStateName(): String {
    return when (this) {
        SSS1 -> "尾单"
        SSS2 -> "尾双"
        else -> "和"
    }
}

/**
 * 总和大、小、和名字
 */
fun Int.sumLargeSmallStateName(): String {
    return when (this) {
        SSS1 -> "总和大"
        SSS2 -> "总和小"
        else -> "和"
    }
}

/**
 * 大单、大双、小单、小双名字
 */
fun Int.largeSmallOddEvenStateName(): String {
    return when (this) {
        SSS1 -> "大单"
        SSS2 -> "大双"
        SSS3 -> "小单"
        else -> "小双"
    }
}

/**
 * 质合数名字
 */
fun Int.primeCompositeStateName(): String {
    return when (this) {
        SSS1 -> "质"
        SSS2 -> "合"
        else -> "和"
    }
}