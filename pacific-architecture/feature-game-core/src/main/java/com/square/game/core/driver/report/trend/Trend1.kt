package com.square.game.core.driver.report.trend

class Trend1(
    val continuousMiss: IntArray,
    val continuousHot: IntArray,
    val totalMiss: IntArray,
    val totalHot: IntArray,
    val hit: IntArray,
    val averageMiss: IntArray,
    val averageHot: IntArray,
    val maxContinuousMiss: IntArray,
    val maxContinuousHot: IntArray,
    val continuousMissSnapshots: List<Triple<String, String, String>>,
    val continuousHotSnapshots: List<String>,
    val totalMissSnapshots: List<String>,
    val totalHotSnapshots: List<String>,
    val hitSnapshots: List<String>,
    val averageMissSnapshots: List<String>,
    val maxContinuousMissSnapshots: List<String>,
    val maxContinuousHotSnapshots: List<String>
)