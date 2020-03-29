package com.square.game.core.base

/**
 * 号码数组转字符串集合
 */
fun IntArray.toStringArray(): Array<String> {
    return this.map { it.toString() }.toTypedArray()
}

/**
 * 号码数组转字符串集合
 */
fun IntArray.toStringList(): List<String> {
    return this.map { it.toString() }
}

/**
 * 字符串形式转整数数组
 */
fun String.toIntArray(): IntArray {
    if (this.isEmpty()) {
        return emptyIntArray
    }
    val list = this.split(STR_COMMA_REG, 0)
    return IntArray(list.size) { list[it].toInt() }
}

/**
 * 升序：从小到大
 */
fun IntArray.ascSortedWith(): List<Int> = this.sortedWith(ascComparatorInt)

/**
 * 降序：从大到小
 */
fun IntArray.desSortedWith(): List<Int> = this.sortedWith(desComparatorInt)

/**
 * 跨度
 */
fun IntArray.ofCross(): Int {
    val sorted = this.ascSortedWith()
    return sorted.last() - sorted.first()
}

/**
 * 根据索引求和
 */
fun IntArray.ofSum(vararg indices: Int): Int {
    var sum = 0
    indices.forEach { sum += this[it] }
    return sum
}

/**
 * 前n个数
 */
fun IntArray.left(n: Int): IntArray = this.range(0, n)

/**
 * 后n个数
 */
fun IntArray.right(n: Int): IntArray = this.range(this.size - n, this.size)

/**
 * 根据索引范围取(toExclusive - from)个数
 */
fun IntArray.range(from: Int, toExclusive: Int): IntArray {
    return IntArray(toExclusive - from) { this[it + from] }
}

/**
 * 筛选单
 */
fun IntArray.filterByOdd(): List<Int> = this.filter { it % 2 == 1 }

/**
 * 筛选双
 */
fun IntArray.filterByEven(): List<Int> = this.filter { it % 2 == 0 }

/**
 * 筛选大
 */
fun IntArray.filterByLarge(n: Int): List<Int> = this.filter { it >= n }

/**
 * 筛选小
 */
fun IntArray.filterBySmall(n: Int): List<Int> = this.filter { it < n }

/**
 * 筛选合单
 */
fun IntArray.filterByUnitSumOdd(): List<Int> = this.filter { it.ofUnitSum() % 2 == 1 }

/**
 * 筛选合双
 */
fun IntArray.filterByUnitSumEven(): List<Int> = this.filter { it.ofUnitSum() % 2 == 0 }

/**
 * 筛选尾数n
 */
fun IntArray.filterByEndN(numbers: IntArray, n: Int): List<Int> {
    return numbers.filter { it.ofEndN() == n }
}

/**
 * 合：把数值的每一位相加
 */
fun Int.ofUnitSum(): Int {
    return if (this < 10) {
        this
    } else {
        val digits = this.toString().toCharArray()
        var result = 0
        digits.forEach { result += it.toString().toInt() }
        result
    }
}

/**
 * 尾数
 */
fun Int.ofEndN(): Int {
    return if (this < 10) {
        this
    } else {
        val value = this.toString()
        value.substring(value.length - 1, value.length).toInt()
    }
}

/**
 * 数字数组
 */
fun range(from: Int, toInclusive: Int): IntArray {
    return IntRange(from, toInclusive).toList().toIntArray()
}


/**
 * 重号个数
 */
fun IntArray.repeatCount(): Int {
    var count = 0
    this.groupBy { it }
        .forEach {
            if (it.value.size >= 2) {
                count++
            }
        }
    return count
}

/**
 * 重号状态：2重号，3重号，4重号......且至少为2以上
 */
fun IntArray.repeatCountState(): Int {
    var state = 1
    this.groupBy { it }
        .forEach {
            state = maxOf(it.value.size, state)
        }
    return state
}

/**
 * 龙虎
 */
fun IntArray.dragonTigerState(index: Int): Int {
    require(this.size % 2 == 0)
    val middle = this.size / 2
    val last = this.size - 1
    if (index >= middle) {
        return SSS_UNKNOWN
    }
    return this[index].largeSmallState(this[last - index])
}

/**
 * 龙虎和，特定情况下忽略计算
 */
fun IntArray.dragonTigerState(index: Int, ignored: Int): Int {
    require(this.size % 2 == 0)
    val middle = this.size / 2
    val last = this.size - 1
    if (index >= middle) {
        return SSS_UNKNOWN
    }
    return this[index].largeSmallState(this[last - index], ignored)
}

/**
 * 龙虎
 */
fun IntArray.dragonTigerState2(index: Int): Int {
    require(this.size % 2 == 0)
    val middle = this.size / 2
    val last = this.size - 1
    if (index >= middle) {
        return SSS_UNKNOWN
    }
    return this[index].largeSmallState2(this[last - index])
}

/**
 * 龙虎和，特定情况下忽略计算
 */
fun IntArray.dragonTigerState2(index: Int, ignored: Int): Int {
    require(this.size % 2 == 0)
    val middle = this.size / 2
    val last = this.size - 1
    if (index >= middle) {
        return SSS_UNKNOWN
    }
    return this[index].largeSmallState2(this[last - index], ignored)
}

/**
 * 验证开奖号码长度
 */
fun IntArray.verifyNumbers(length: Int): Boolean = this.isEmpty() || this.size == length