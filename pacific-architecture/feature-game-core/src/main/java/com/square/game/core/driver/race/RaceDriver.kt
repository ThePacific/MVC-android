package com.square.game.core.driver.race

import com.square.game.core.base.NumberStateRunner
import com.square.game.core.base.dragonTigerState
import com.square.game.core.base.largeSmallState
import com.square.game.core.base.oddEvenState
import com.square.game.core.entities.GResult

object RaceDriver : NumberStateRunner {

    override fun applyStates(gResult: GResult) {
        val sumL1L2 = gResult.numbers[0] + gResult.numbers[1]
        gResult.setState(
            sumL1L2,// 冠亚和
            sumL1L2.largeSmallState(12),// 冠亚和大小
            sumL1L2.oddEvenState()// 冠亚和单双
        )

        var number: Int
        for (i in gResult.numbers.indices) {
            number = gResult.numbers[i]
            gResult.setState(
                number,// 号码
                gResult.numbers.dragonTigerState(i)// 龙虎
            )
        }
    }
}