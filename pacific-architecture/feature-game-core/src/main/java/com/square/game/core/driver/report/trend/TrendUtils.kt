package com.square.game.core.driver.report.trend

import com.square.game.core.base.*

/**
 * 计算走势
 * @param xResults
 * @param xTargets
 */
fun createTrend1(
    xResults: List<List<String>>,
    xTargets: Array<String>
): Trend1 {
    val continuousMiss = IntArray(xTargets.size) { 0 }
    val continuousHot = IntArray(xTargets.size) { 0 }
    val totalMiss = IntArray(xTargets.size) { 0 }
    val totalHot = IntArray(xTargets.size) { 0 }
    val hit = IntArray(xTargets.size) { 0 }
    val averageMiss = IntArray(xTargets.size) { 0 }
    val averageHot = IntArray(xTargets.size) { 0 }
    val maxContinuousMiss = IntArray(xTargets.size) { 0 }
    val maxContinuousHot = IntArray(xTargets.size) { 0 }
    val hitIndices = ArrayList<Int>()
    val hitName = ArrayList<String>()

    val continuousMissSnapshots = ArrayList<Triple<String, String, String>>()
    val continuousHotSnapshots = ArrayList<String>()
    val totalMissSnapshots = ArrayList<String>()
    val totalHotSnapshots = ArrayList<String>()
    val hitSnapshots = ArrayList<String>()
    val averageMissSnapshots = ArrayList<String>()
    val maxContinuousMissSnapshots = ArrayList<String>()
    val maxContinuousHotSnapshots = ArrayList<String>()

    var xTarget: String
    var xResult: List<String>
    for (row in xResults.indices) {
        xResult = xResults[row]
        hitIndices.clear()
        hitName.clear()
        if (xResult.isNullOrEmpty()) {
            continuousMissSnapshots.add(emptyStringTriple())
            totalMissSnapshots.add(STR_EMPTY)
            continuousHotSnapshots.add(STR_EMPTY)
            totalHotSnapshots.add(STR_EMPTY)
            hitSnapshots.add(STR_EMPTY)
            averageMissSnapshots.add(STR_EMPTY)
            maxContinuousMissSnapshots.add(STR_EMPTY)
            maxContinuousHotSnapshots.add(STR_EMPTY)
        } else {
            for (column in xTargets.indices) {
                xTarget = xTargets[column]
                if (hitTarget(xResult, xTarget)) {
                    hitIndices.add(column)
                    hitName.add(xTarget)

                    continuousMiss[column] = 0

                    hit[column] = hitCount(xResult, xTarget)
                    totalHot[column] = totalHot[column] + hit[column]
                    // continuousHot[column] = continuousHot[column] + 1
                    continuousHot[column] = continuousHot[column] + hit[column]

                    if (maxContinuousHot[column] < continuousHot[column]) {
                        maxContinuousHot[column] = continuousHot[column]
                    }
                } else {
                    continuousMiss[column] = continuousMiss[column] + 1
                    continuousHot[column] = 0
                    hit[column] = 0
                    totalMiss[column] = totalMiss[column] + 1
                    if (maxContinuousMiss[column] < continuousMiss[column]) {
                        maxContinuousMiss[column] = continuousMiss[column]
                    }
                }
            }
            continuousMissSnapshots.add(
                Triple(
                    continuousMiss.joinToString(STR_COMMA),
                    hitIndices.joinToString(STR_COMMA),
                    hitName.joinToString(STR_COMMA)
                )
            )
            totalMissSnapshots.add(totalMiss.joinToString(STR_COMMA))
            continuousHotSnapshots.add(continuousHot.joinToString(STR_COMMA))
            totalHotSnapshots.add(totalHot.joinToString(STR_COMMA))
            hitSnapshots.add(hit.joinToString(STR_COMMA))
            for (column in xTargets.indices) {
                if (totalHot[column] == 0) {
                    averageMiss[column] = totalMiss[column]
                } else {
                    averageMiss[column] = totalMiss[column] / totalHot[column]
                }
                if (totalMiss[column] == 0) {
                    averageHot[column] = totalHot[column]
                } else {
                    averageHot[column] = totalHot[column] / totalMiss[column]
                }
            }
            averageMissSnapshots.add(averageMiss.joinToString(STR_COMMA))
            maxContinuousMissSnapshots.add(maxContinuousMiss.joinToString(STR_COMMA))
            maxContinuousHotSnapshots.add(maxContinuousHot.joinToString(STR_COMMA))
        }
    }
    return Trend1(
        continuousMiss,
        continuousHot,
        totalMiss,
        totalHot,
        hit,
        averageMiss,
        averageHot,
        maxContinuousMiss,
        maxContinuousHot,
        continuousMissSnapshots,
        continuousHotSnapshots,
        totalMissSnapshots,
        totalHotSnapshots,
        hitSnapshots,
        averageMissSnapshots,
        maxContinuousMissSnapshots,
        maxContinuousHotSnapshots
    )
}

/**
 * 计算走势
 * @param xResults
 * @param xTargets
 */
fun createTrend2(
    xResults: List<List<String>>,
    xTargets: Array<String>
): Trend2 {
    val continuousMiss = IntArray(xTargets.size) { 0 }
    val continuousHot = IntArray(xTargets.size) { 0 }
    val totalMiss = IntArray(xTargets.size) { 0 }
    val totalHot = IntArray(xTargets.size) { 0 }
    val hit = IntArray(xTargets.size) { 0 }
    val averageMiss = IntArray(xTargets.size) { 0 }
    val averageHot = IntArray(xTargets.size) { 0 }
    val maxContinuousMiss = IntArray(xTargets.size) { 0 }
    val maxContinuousHot = IntArray(xTargets.size) { 0 }
    val hitIndices = ArrayList<Int>()
    val hitName = ArrayList<String>()

    val continuousMissSnapshots = ArrayList<Triple<IntArray, IntArray, Array<String>>>()
    val continuousHotSnapshots = ArrayList<IntArray>()
    val totalMissSnapshots = ArrayList<IntArray>()
    val totalHotSnapshots = ArrayList<IntArray>()
    val hitSnapshots = ArrayList<IntArray>()
    val averageMissSnapshots = ArrayList<IntArray>()
    val maxContinuousMissSnapshots = ArrayList<IntArray>()
    val maxContinuousHotSnapshots = ArrayList<IntArray>()

    var xTarget: String
    var xResult: List<String>
    for (row in xResults.indices) {
        xResult = xResults[row]
        hitIndices.clear()
        hitName.clear()
        if (xResult.isNullOrEmpty()) {
            continuousMissSnapshots.add(Triple(emptyIntArray, emptyIntArray, emptyStringArray))
            totalMissSnapshots.add(emptyIntArray)
            continuousHotSnapshots.add(emptyIntArray)
            totalHotSnapshots.add(emptyIntArray)
            hitSnapshots.add(emptyIntArray)
            averageMissSnapshots.add(emptyIntArray)
            maxContinuousMissSnapshots.add(emptyIntArray)
            maxContinuousHotSnapshots.add(emptyIntArray)
        } else {
            for (column in xTargets.indices) {
                xTarget = xTargets[column]
                if (hitTarget(xResult, xTarget)) {
                    hitIndices.add(column)
                    hitName.add(xTarget)

                    continuousMiss[column] = 0
                    hit[column] = hitCount(xResult, xTarget)
                    totalHot[column] = totalHot[column] + hit[column]
                    // continuousHot[column] = continuousHot[column] + 1
                    continuousHot[column] = continuousHot[column] + hit[column]
                    if (maxContinuousHot[column] < continuousHot[column]) {
                        maxContinuousHot[column] = continuousHot[column]
                    }
                } else {
                    continuousMiss[column] = continuousMiss[column] + 1
                    continuousHot[column] = 0
                    hit[column] = 0
                    totalMiss[column] = totalMiss[column] + 1
                    if (maxContinuousMiss[column] < continuousMiss[column]) {
                        maxContinuousMiss[column] = continuousMiss[column]
                    }
                }
            }
            continuousMissSnapshots.add(
                Triple(
                    continuousMiss.copyOf(),
                    hitIndices.toIntArray(),
                    hitName.toTypedArray()
                )
            )
            totalMissSnapshots.add(totalMiss.copyOf())
            continuousHotSnapshots.add(continuousHot.copyOf())
            totalHotSnapshots.add(totalHot.copyOf())
            hitSnapshots.add(hit.copyOf())
            for (column in xTargets.indices) {
                if (totalHot[column] == 0) {
                    averageMiss[column] = totalMiss[column]
                } else {
                    averageMiss[column] = totalMiss[column] / totalHot[column]
                }
                if (totalMiss[column] == 0) {
                    averageHot[column] = totalHot[column]
                } else {
                    averageHot[column] = totalHot[column] / totalMiss[column]
                }
            }
            averageMissSnapshots.add(averageMiss.copyOf())
            maxContinuousMissSnapshots.add(maxContinuousMiss.copyOf())
            maxContinuousHotSnapshots.add(maxContinuousHot.copyOf())
        }
    }
    return Trend2(
        continuousMiss,
        continuousHot,
        totalMiss,
        totalHot,
        hit,
        averageMiss,
        averageHot,
        maxContinuousMiss,
        maxContinuousHot,
        continuousMissSnapshots,
        continuousHotSnapshots,
        totalMissSnapshots,
        totalHotSnapshots,
        hitSnapshots,
        averageMissSnapshots,
        maxContinuousMissSnapshots,
        maxContinuousHotSnapshots
    )
}

/**
 * 计算方式：
 * 在页面统计奖期内，查看每个位置上每个号码出现次数。
 * 每一位上10个号码中，出现次数最多的三个号码为热号、出现次数最少的三个号码为冷号，其他为温号。
 * 注意：如果有多个号码出现次数相同，则优先选择小的号码
 * 1个号码：热号
 * 2个号码：1冷1热
 * 3~5个号码：1冷1热 N温
 * 6~8个号码：2冷2热 N温
 * 9个号码：3冷3热 N温
 */
fun getLrmStates(hitSnapshotSet: List<String>, columnCount: Int): IntArray {
    val counters = IntArray(columnCount) { 0 }
    hitSnapshotSet.forEach {
        it.toIntArray().also { hits ->
            for (i in 0 until columnCount) {
                counters[i] = counters[i] + hits[i]
            }
        }
    }
    val ascCounters = counters.ascSortedWith()
    val leftEndIndex: Int
    val rightStartIndex: Int
    val sLen: Int
    when (columnCount) {
        1 -> {
            leftEndIndex = 0
            rightStartIndex = 0
            sLen = 1
        }
        in 2..5 -> {
            leftEndIndex = 0
            rightStartIndex = columnCount - 1
            sLen = 1
        }
        in 6..8 -> {
            leftEndIndex = 1
            rightStartIndex = columnCount - 2
            sLen = 2
        }
        else -> {
            leftEndIndex = 2
            rightStartIndex = columnCount - 3
            sLen = 3
        }
    }

    val states = IntArray(columnCount) { 0 }
    var index: Int
    for (i in 0 until columnCount) {
        index = ascCounters.indexOf(counters[i])

        states[i] = when {
            index <= leftEndIndex -> {
                if (states.filter { s -> 1 == s }.count() >= sLen) 2 else 1
            }
            index >= rightStartIndex -> {
                if (states.filter { s -> 3 == s }.count() >= sLen) 2 else 3
            }
            else -> 2
        }
    }
    return states
}

fun splitTriple1(
    originalTriple: Triple<String, String, String>,
    vararg counts: Int
): List<Triple<String, String, String>> {
    val first = originalTriple.first.split(STR_COMMA_REG)
    val second = originalTriple.second.split(STR_COMMA_REG)
    val third = originalTriple.third.split(STR_COMMA_REG)
    require(counts.isNotEmpty())
    require(counts.size == second.size)
    require(third.size == third.size)
    val list = ArrayList<Triple<String, String, String>>()
    var offset = 0
    for (j in counts.indices) {
        list.add(
            Triple(
                first.subList(offset, counts[j] + offset).joinToString(STR_COMMA),
                (second[j].toInt() - offset).toString(),
                third[j]
            )
        )
        offset += counts[j]
    }
    return list
}

fun splitTriple2(
    originalTriple: Triple<IntArray, IntArray, Array<String>>,
    vararg counts: Int
): List<Triple<IntArray, IntArray, Array<String>>> {
    val first = originalTriple.first.toList()
    val second = originalTriple.second
    val third = originalTriple.third
    require(counts.isNotEmpty())
    require(counts.size == second.size)
    require(third.size == third.size)

    val list = ArrayList<Triple<IntArray, IntArray, Array<String>>>()
    var offset = 0
    for (j in counts.indices) {
        list.add(
            Triple(
                first.subList(offset, counts[j] + offset).toIntArray(),
                intArrayOf(second[j] - offset),
                arrayOf(third[j])
            )
        )
        offset += counts[j]
    }
    return list
}

fun mergeTriples(
    vararg triples: List<Triple<String, String, String>>
): List<Triple<String, String, String>> {
    val list = ArrayList<Triple<String, String, String>>()
    for (row in triples[0].indices) {
        list.add(mergeTriples(*Array(triples.size) { triples[it][row] }))
    }
    return list
}

fun mergeTriples(vararg triples: Triple<String, String, String>): Triple<String, String, String> {
    var offset = 0
    return Triple(
        Array(triples.size) {
            triples[it].first
        }.joinToString(","),
        Array(triples.size) { i ->
            triples[i].second.toIntArray()
                .map { it + offset }
                .also { offset += triples[i].first.split(STR_COMMA_REG).size }
                .joinToString(",")
        }.joinToString(","),
        Array(triples.size) {
            triples[it].third
        }.joinToString(",")
    )
}

fun triplesFirst(triples: List<Triple<String, String, String>>): List<String> {
    return triplesN(triples, 1)
}

fun triplesSecond(triples: List<Triple<String, String, String>>): List<String> {
    return triplesN(triples, 2)
}

fun triplesThird(triples: List<Triple<String, String, String>>): List<String> {
    return triplesN(triples, 3)
}

private fun triplesN(triples: List<Triple<String, String, String>>, n: Int): List<String> {
    val list = ArrayList<String>()
    when (n) {
        1 -> triples.forEach { list.add(it.first) }
        2 -> triples.forEach { list.add(it.second) }
        3 -> triples.forEach { list.add(it.third) }
        else -> throw UnsupportedOperationException()
    }
    return list
}

private fun hitTarget(result: List<String>, target: String): Boolean {
    return result.contains(target)
}

private fun hitCount(result: List<String>, target: String): Int {
    var count = 0
    result.forEach {
        if (target == it) {
            count++
        }
    }
    return count
}

private fun emptyStringTriple(): Triple<String, String, String> {
    return Triple(STR_EMPTY, STR_EMPTY, STR_EMPTY)
}