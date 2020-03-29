package com.square.game.core.driver.report.road

class Road(
    val type: Int,
    val columnCount: Int,
    val points: ArrayList<RoadPoint>,
    val table: IntArray,
    val xResults: List<Pair<Int, String>>
) {
    var isDeprecated = false

    var positive: RoadPoint? = null

    var negative: RoadPoint? = null

    var positiveCount = 0
        private set

    var negativeCount = 0
        private set

    var neutralCount = 0
        private set

    var counts: String = ""
        private set
}