package com.square.game.core.driver.report.road

import com.square.game.core.base.SSS1
import com.square.game.core.base.SSS2
import com.square.game.core.base.emptyIntArray
import com.square.guava.domain.unsupportedOperationException

const val TYPE_ROAD_LOTTERY = 1
const val TYPE_ROAD_BIG = 2
const val TYPE_ROAD_BIG_EYE = 3
const val TYPE_ROAD_SMALL = 4
const val TYPE_ROAD_BUG = 5

private const val MAX_ROW_INDEX = 5
private const val MAX_ROW_COUNT = 6
private const val TABLE_EMPTY_POINT = -9

fun roadName(type: Int): String {
    return when (type) {
        TYPE_ROAD_LOTTERY -> "Lottery路"
        TYPE_ROAD_BIG -> "Big路"
        TYPE_ROAD_BIG_EYE -> "BigEye路"
        TYPE_ROAD_SMALL -> "Small路"
        else -> "Bug路"
    }
}

/**
 * SSS3 为和，SSS1为正，SSS2为负
 */
fun createFigureBigRoad(results: List<Pair<Int, String>>): Road {
    return createBigRoad(TYPE_ROAD_BIG, results)
}

/**
 * SSS3 为和，SSS1为正，SSS2为负
 */
fun createFutureRoads(results: List<Pair<Int, String>>): ArrayList<Road> {
    return createRoads(results).apply {
        setFuture(SSS1, this)
        setFuture(SSS2, this)
    }
}

/**
 * SSS3 为和，SSS1为正，SSS2为负
 */
fun createRoads(results: List<Pair<Int, String>>): ArrayList<Road> {
    if (results.isEmpty()) {
        return arrayListOf()
    }
    val bigRoadXXX = createBigRoadXXX(TYPE_ROAD_BIG, results)
    return arrayListOf(
            createLotteryRoad(results),
            createBigRoad(TYPE_ROAD_BIG, results),
            safeCreateBigRoad(
                    TYPE_ROAD_BIG_EYE,
                    asSiblingResults(TYPE_ROAD_BIG_EYE, bigRoadXXX, MAX_ROW_COUNT)
            ),
            safeCreateBigRoad(
                    TYPE_ROAD_SMALL,
                    asSiblingResults(TYPE_ROAD_SMALL, bigRoadXXX, MAX_ROW_COUNT)
            ),
            safeCreateBigRoad(
                    TYPE_ROAD_BUG,
                    asSiblingResults(TYPE_ROAD_BUG, bigRoadXXX, MAX_ROW_COUNT)
            )
    )
}

private fun createLotteryRoad(xResults: List<Pair<Int, String>>): Road {
    val points = ArrayList<RoadPoint>()
    var row = 0
    var column = 0
    var result: Pair<Int, String>
    for (element in xResults) {
        result = element
        RoadPoint.obtain().apply {
            this.status = result.first
            this.text = result.second
            this.row = row
            this.column = column
            points.add(this)
        }
        if (row == MAX_ROW_INDEX) {
            row = 0
            column += 1
        } else {
            row += 1
        }
    }
    return Road(
            TYPE_ROAD_LOTTERY,
            if (row == 0) column + 1 else column + 2,
            points,
            emptyIntArray,
            xResults
    )
}

private fun safeCreateBigRoad(type: Int, results: List<Pair<Int, String>>): Road {
    return try {
        createBigRoad(type, results)
    } catch (ignored: Exception) {
        createEmptyRoad(type).apply {
            isDeprecated = true
        }
    }
}

private fun createBigRoad(type: Int, xResults: List<Pair<Int, String>>): Road {
    if (xResults.isEmpty()) {
        return createEmptyRoad(type)
    }

    val columnCount = xResults.size
    val table = IntArray(columnCount * MAX_ROW_COUNT) { TABLE_EMPTY_POINT }
    val points = ArrayList<RoadPoint>()

    var prePoint = RoadPoint.obtain().also {
        it.status = xResults[0].first
        it.text = xResults[0].second
        it.row = 0
        it.column = 0
        updateTable(it, table, columnCount)
        points.add(it)
    }

    var curPoint: RoadPoint
    var result: Pair<Int, String>
    for (i in 1 until xResults.size) {
        result = xResults[i]
        curPoint = RoadPoint.obtain().apply {
            status = result.first
            text = result.second
        }
        curPoint.moveAction = if (equalsStatus(curPoint, points)) {
            goVertical(prePoint, curPoint, table, columnCount, MAX_ROW_INDEX)
        } else {
            goHorizontal(prePoint, curPoint, table)
        }
        updateTable(curPoint, table, columnCount)
        points.add(curPoint)
        prePoint = curPoint
    }
    return Road(
            type,
            trimTableColumnCount(table, columnCount),
            points,
            table,
            xResults
    )
}

private fun createBigRoadXXX(
        type: Int,
        results: List<Pair<Int, String>>
): Road {
    if (results.isEmpty()) {
        return createEmptyRoad(type)
    }
    val columnCount = results.size
    val table = IntArray(columnCount * MAX_ROW_COUNT) { TABLE_EMPTY_POINT }
    val points = ArrayList<RoadPoint>()

    var result: Pair<Int, String>
    var from = -1
    var prePoint = RoadPoint.obtain().also {
        // WTF continue??? break??? for i=0
        // 核心数据在这里生成，完全忽略了和
        for (i in results.indices) {
            result = results[i]
            if (isNeutralState(result.first)) {
                continue
            } else {
                from = i + 1
                it.status = results[0].first
                it.text = results[0].second
                it.row = 0
                it.column = 0
                updateTable(it, table, columnCount)
                points.add(it)
                break
            }
        }
        if (from < 0) {
            unsupportedOperationException()
        }
    }

    var curPoint: RoadPoint
    for (i in from until results.size) {
        result = results[i]
        if (isNeutralState(result.first)) {
            continue
        }
        curPoint = RoadPoint.obtain().apply {
            status = result.first
            text = result.second
        }
        curPoint.moveAction = if (equalsStatus(curPoint, points)) {
            goVertical(prePoint, curPoint, table, columnCount, MAX_ROW_INDEX)
        } else {
            goHorizontal(prePoint, curPoint, table)
        }
        updateTable(curPoint, table, columnCount)
        points.add(curPoint)
        prePoint = curPoint
    }
    return Road(
            type,
            trimTableColumnCount(table, columnCount),
            points,
            table,
            results
    )
}

private fun asSiblingResults(
        type: Int,
        bigRoad: Road,
        rowCount: Int
): List<Pair<Int, String>> {
    val results = ArrayList<Pair<Int, String>>()
    var preRow: Int
    var row: Int
    var status: Int
    var point: RoadPoint
    var preColumnToLeft: Int
    var preColumnToDown: Int

    val points = bigRoad.points
    val table = bigRoad.table
    val columnCount = bigRoad.table.size / rowCount
    var index: Int = -1
    loop@ for (i in 0 until points.size) {
        // 先从座标1(第2列第2行)分析，若座标1无庄或闲，则以座标2(第3列第1行)开始分析
        point = points[i]
        when (type) {
            TYPE_ROAD_BIG_EYE -> {
                if (
                        (point.row == 1 && point.column == 1) ||
                        (point.row == 0 && point.column == 2)
                ) {
                    index = i
                    break@loop
                }
            }
            TYPE_ROAD_SMALL -> {
                if (
                        (point.row == 1 && point.column == 2) ||
                        (point.row == 0 && point.column == 3)
                ) {
                    index = i
                    break@loop
                }
            }
            TYPE_ROAD_BUG -> {
                if (
                        (point.row == 1 && point.column == 3) ||
                        (point.row == 0 && point.column == 4)
                ) {
                    index = i
                    break@loop
                }
            }
            else -> {
            }
        }
    }

    if (index == -1) {
        return results
    }

    for (j in index until points.size) {
        point = points[j]

        if (isNeutralState(point.status)) {
            continue
        }

        preRow = (point.row - 1) * columnCount
        row = point.row * columnCount

        preColumnToLeft = when (type) {
            TYPE_ROAD_BIG_EYE -> point.column - 2
            TYPE_ROAD_SMALL -> point.column - 3
            TYPE_ROAD_BUG -> point.column - 4
            else -> 0
        }
        preColumnToDown = preColumnToLeft + 1

        status = if (point.moveAction == 1) {// 向右换列操作
            // 以大路最新的结果，水平方式比对它的前一列与前二(N)列结果位置，如果是齐整的，则画红圈，否则画蓝圈
            if (verifyAlign(table, columnCount, preColumnToLeft, point.column - 1, rowCount - 1)) {
                SSS1
            } else {
                SSS2
            }
        } else {// 向下换行操作
            // 以大路最新的结果，水平方式跟前一(N)列作对比，如无结果的状态有且仅有一行，则于大眼路画蓝圈，否则画红圈
            if (
                    table[row + preColumnToDown] == TABLE_EMPTY_POINT &&
                    table[preRow + preColumnToDown] != TABLE_EMPTY_POINT
            ) {
                SSS2
            } else {
                SSS1
            }
        }
        results.add(Pair(status, point.text))
    }
    return results
}

private fun verifyAlign(
        table: IntArray,
        columnCount: Int,
        column1: Int,
        column2: Int,
        maxRow: Int
): Boolean {
    var row: Int
    var v1: Int
    var v2: Int
    var align = true

    for (i in 0..maxRow) {
        row = i * columnCount
        v1 = table[row + column1]
        v2 = table[row + column2]
        if (isNeutralState(v1)) {
            v1 = TABLE_EMPTY_POINT
        }
        if (isNeutralState(v2)) {
            v2 = TABLE_EMPTY_POINT
        }

        if (
                (v1 == TABLE_EMPTY_POINT && v2 == TABLE_EMPTY_POINT) ||
                (v1 != TABLE_EMPTY_POINT && v2 != TABLE_EMPTY_POINT)
        ) {
            continue
        } else {
            return false
        }
    }
    return align
}

private fun goVertical(
        prePoint: RoadPoint,
        curPoint: RoadPoint,
        table: IntArray,
        columnCount: Int,
        maxRow: Int
): Int {
    val preBool = if (prePoint.row == 0) {
        true
    } else {
        table[(prePoint.row - 1) * columnCount + prePoint.column] != TABLE_EMPTY_POINT
    }
    return if (
            prePoint.row < maxRow &&
            (table[(prePoint.row + 1) * columnCount + prePoint.column] == TABLE_EMPTY_POINT) &&
            preBool
    ) {
        // 未到达最后一行， 并且往下一行未被占用且上一行，直接换行继续往下走
        curPoint.row = prePoint.row + 1
        curPoint.column = prePoint.column
        0// 向下换行操作
    } else {
        // 已到达最后一行或者往下一行已被占用，强制换列向右走
        curPoint.row = prePoint.row
        curPoint.column = prePoint.column + 1
        1 // 向右换列操作
    }
}

private fun goHorizontal(prePoint: RoadPoint, curPoint: RoadPoint, table: IntArray): Int {
    var validateY = -1
    for (c in prePoint.column + 1 downTo 1) {
        if (table[c] == TABLE_EMPTY_POINT && table[c - 1] != TABLE_EMPTY_POINT) {
            // 从第一行中找到换列的位置
            validateY = c
            break
        }
    }
    if (validateY == -1) {
        unsupportedOperationException("Invalidate column")
    }
    curPoint.row = 0
    curPoint.column = validateY
    return 1 // 向右换列操作
}

private fun updateTable(p: RoadPoint, table: IntArray, columnCount: Int) {
    val index = p.row * columnCount + p.column
    if (table[index] != TABLE_EMPTY_POINT) {
        unsupportedOperationException()
    } else {
        table[index] = p.status
    }
}

private fun equalsStatus(curPoint: RoadPoint, points: List<RoadPoint>): Boolean {
    var prePoint: RoadPoint? = null
    for (i in points.size - 1 downTo 0) {
        if (!isNeutralState(points[i].status)) {
            prePoint = points[i]
            break
        }
    }
    return prePoint?.equalsStatus(curPoint) ?: true
}

private fun trimTableColumnCount(table: IntArray, columnCount: Int): Int {
    for (c in 0 until columnCount) {
        if (isTableColumnEmpty(table, columnCount, c)) {
            return c + 1
        }
    }
    return columnCount + 1
}

private fun isTableColumnEmpty(
        table: IntArray,
        columnCount: Int,
        column: Int
): Boolean {
    var emptyCount = 0
    for (row in 0 until MAX_ROW_COUNT) {
        if (table[row * columnCount + column] == TABLE_EMPTY_POINT) {
            emptyCount++
        } else {
            return false
        }
    }
    return emptyCount == MAX_ROW_COUNT
}

private fun setFuture(futureStatus: Int, roads: ArrayList<Road>) {
    if (roads.isEmpty()) {
        return
    }

    val bigRoad = roads[0]
    val futurePoint: RoadPoint = RoadPoint.obtain()
    var point: RoadPoint
    for (i in 0 until bigRoad.points.size) {
        point = bigRoad.points[i]
        if (point.status == futureStatus) {
            futurePoint.setValue(point)
            break
        }
    }
    if (futurePoint.isEmpty()) {
        return
    }
    val list = ArrayList<Pair<Int, String>>().apply {
        addAll(bigRoad.xResults)
        add(Pair(futurePoint.status, futurePoint.text))
    }
    val futureRoads = createRoads(list)
    if (futureStatus == SSS1) {
        for (i in 0 until futureRoads.size) {
            roads[i].positive = getLastPoint(futureRoads[i].points)
        }
    } else {
        for (i in 0 until futureRoads.size) {
            roads[i].negative = getLastPoint(futureRoads[i].points)
        }
    }
}

private fun getLastPoint(points: ArrayList<RoadPoint>): RoadPoint? {
    return if (points.isEmpty()) null else points[points.size - 1]
}

private fun createEmptyRoad(type: Int): Road {
    return Road(type, 0, arrayListOf(), emptyIntArray, emptyList())
}

fun isNeutralState(state: Int): Boolean {
    return state != SSS1 && state != SSS2
}