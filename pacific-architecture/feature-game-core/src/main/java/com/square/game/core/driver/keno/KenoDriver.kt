package com.square.game.core.driver.keno

import com.square.game.core.base.*
import com.square.game.core.base.NumberStateRunner
import com.square.game.core.entities.GResult

object KenoDriver : NumberStateRunner {

    override fun applyStates(gResult: GResult) {
        val sum = gResult.numbers.sum()
        val sumLargeSmallState = sum.largeSmallState2(810)
        val sumOddEvenState = sum.oddEvenState()
        gResult.setState(
            sum,// 总和
            sumLargeSmallState,// 总和大小和
            sumOddEvenState,// 总和单双
            largeSmallOddEvenState(sumLargeSmallState, sumOddEvenState),// 总和大单、大双、小单、小双
            sumState(sum)// 总和状态
        )

        gResult.setState(*gResult.numbers)// 号码
    }

    /**
     * 总和状态
     */
    @JvmStatic
    fun sumState(sum: Int): Int {
        return when (sum) {
            in 211..695 -> SSS1
            in 696..763 -> SSS2
            in 764..855 -> SSS3
            in 856..923 -> SSS4
            else -> SSS5
        }
    }
}