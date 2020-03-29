package com.square.game.core.base

object Game {

    const val gameTypeA = 1
    const val gameTypeB = 2
    const val gameTypeC = 3
    const val gameTypeD = 4
    const val gameTypeE = 5

    const val categoryKeno: Long = 1// Keno系列
    const val categoryMark6: Long = 3// 六合彩系列
    const val categoryEleven5: Long = 2// 11选5系列
    const val categoryHappy10: Long = 1// 10分系列
    const val categoryInstant: Long = 1// 时时彩系列
    const val categoryPeking28: Long = 1// 28分系列
    const val categoryRace: Long = 6// 赛车系列
    const val categoryRapid3: Long = 7// 快三系列
    const val categoryD3: Long = 4// 3D系列
    const val categoryHappy: Long = 5// 快乐彩系列
    const val categoryLine35: Long = 8// 排列35系列


    /**
     * 各个彩系开奖号码长度
     */
    @JvmStatic
    fun length(categoryId: Long): Int {
        return when (categoryId) {
            categoryMark6 -> 7
            categoryEleven5 -> 5
            categoryInstant -> 5
            categoryRace -> 10
            categoryD3 -> 3
            categoryHappy -> 20
            categoryLine35 -> 5
            categoryRapid3 -> 3
            categoryKeno -> 20
            else -> 0
        }
    }
}