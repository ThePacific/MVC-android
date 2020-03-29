package com.square.game.core.driver.report.trend

class Trend2(
    val continuousMiss: IntArray,
    val continuousHot: IntArray,
    val totalMiss: IntArray,
    val totalHot: IntArray,
    val hit: IntArray,
    val averageMiss: IntArray,
    val averageHot: IntArray,
    val maxContinuousMiss: IntArray,
    val maxContinuousHot: IntArray,
    val continuousMissSnapshots: List<Triple<IntArray, IntArray, Array<String>>>,
    val continuousHotSnapshots: List<IntArray>,
    val totalMissSnapshots: List<IntArray>,
    val totalHotSnapshots: List<IntArray>,
    val hitSnapshots: List<IntArray>,
    val averageMissSnapshots: List<IntArray>,
    val maxContinuousMissSnapshots: List<IntArray>,
    val maxContinuousHotSnapshots: List<IntArray>
)