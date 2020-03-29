package com.square.game.core.driver.eleven5

import com.square.game.core.base.*
import com.square.game.core.driver.report.ChartRunner
import com.square.game.core.entities.GResult

object Eleven5Driver : NumberStateRunner, ChartRunner {

    override fun applyStates(gResult: GResult) {
        val sum = gResult.numbers.sum()
        gResult.setState(
            sum,// 总和
            sum.largeSmallState2(30),// 总和大小
            sum.oddEvenState(),// 总和单双
            sum.ofEndN().largeSmallState(5),// 总和尾大尾小
            dragonTigerState(gResult.numbers[0], gResult.numbers[4])// 总和龙虎
        )

        gResult.numbers.forEach {
            gResult.setState(
                it,// 号码
                it.largeSmallState(6),// 大小
                it.oddEvenState()// 单双
            )
        }
    }

    override fun applyFigureChart(gResults: List<GResult>) {
    }

    override fun applyRoadChart(gResults: List<GResult>) {
    }

    override fun applyTrendChart(gResults: List<GResult>) {
    }
}