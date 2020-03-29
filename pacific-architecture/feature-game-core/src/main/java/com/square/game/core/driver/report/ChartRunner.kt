package com.square.game.core.driver.report

import com.square.game.core.entities.GResult

interface ChartRunner {

    fun applyFigureChart(gResults: List<GResult>)

    fun applyRoadChart(gResults: List<GResult>)

    fun applyTrendChart(gResults: List<GResult>)
}