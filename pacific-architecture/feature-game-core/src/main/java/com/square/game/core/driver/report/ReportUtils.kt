package com.square.game.core.driver.report

import com.square.game.core.entities.GResult
import java.awt.Point

/**
 * 计算多个维度
 * @param results
 * @param targetsArray 维度数组，如 [\[大,小,和], \[单,双,和], \[龙,虎,和]]
 * @param targetIndices 每一个维度位置
 */
fun getReport(
    results: List<GResult>,
    targetsArray: Array<IntArray>,
    targetIndices: IntArray
): List<Array<Point>> {
    require(targetsArray.size == targetIndices.size)
    val hotArray = Array(targetsArray.size) { IntArray(targetsArray[it].size) { 0 } }
    val continuousMissArray = Array(targetsArray.size) { IntArray(targetsArray[it].size) { 0 } }
    var states: List<Int>
    var targets: IntArray
    var targetIndex: Int
    results.forEach {
        states = it.states

        for (i in targetsArray.indices) {
            targetIndex = targetIndices[i]
            targets = targetsArray[i]

            for (j in targets.indices) {
                if (targets[j] == states[targetIndex]) {
                    continuousMissArray[i][j] = 0
                    hotArray[i][j] = hotArray[i][j] + 1
                } else {
                    continuousMissArray[i][j] = continuousMissArray[i][j] + 1
                }
            }
        }
    }

    return ArrayList<Array<Point>>().apply {
        for (i in targetsArray.indices) {
            add(asPoints(hotArray[i], continuousMissArray[i]))
        }
    }
}

/**
 * 计算单个维度
 * @param results
 * @param targets 单个维度，如 [1,2,3,4,5,6]
 * @param targetIndices 与维度相关的所有位置
 */
fun getReport(
    results: List<GResult>,
    targets: IntArray,
    targetIndices: IntArray
): Array<Point> {
    val hot = IntArray(targets.size) { 0 }
    val continuousMiss = IntArray(targets.size) { 0 }
    var hitCount: Int
    results.forEach {
        for (j in targets.indices) {
            hitCount = hitCount(targets[j], it.states, targetIndices)
            if (hitCount > 0) {
                continuousMiss[j] = 0
                hot[j] = hot[j] + hitCount
            } else {
                continuousMiss[j] = continuousMiss[j] + 1
            }
        }
    }
    return asPoints(hot, continuousMiss)
}

private fun asPoints(hot: IntArray, miss: IntArray): Array<Point> {
    return Array(hot.size) {
        Point(hot[it], miss[it])
    }
}

private fun hitCount(target: Int, states: List<Int>, targetIndices: IntArray): Int {
    var count = 0
    targetIndices.forEach {
        if (target == states[it]) {
            count++
        }
    }
    return count
}