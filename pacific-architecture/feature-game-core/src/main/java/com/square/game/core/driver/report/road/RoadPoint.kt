package com.square.game.core.driver.report.road

import com.square.game.core.base.SSS_UNKNOWN
import com.square.guava.domain.Pools2
import java.util.*
import kotlin.collections.ArrayList

class RoadPoint private constructor() {
    var row: Int = -1
    var column: Int = -1
    var text: String = ""
    var status: Int = SSS_UNKNOWN
    var moveAction: Int = -1

    var x: Float = -1f
        private set
    var y: Float = -1f
        private set

    private fun empty(): RoadPoint {
        row = -1
        column = -1
        text = ""
        status = SSS_UNKNOWN
        x = -1f
        y = -1f
        moveAction = -1
        return this
    }

    fun setValue(that: RoadPoint) {
        this.row = that.row
        this.column = that.column
        this.text = that.text
        this.status = that.status
        this.x = that.x
        this.y = that.y
        this.moveAction = that.moveAction
    }

    fun isEmpty(): Boolean = row < 0 || column < 0

    fun updateXY(size: Int) {
        x = (column * size + size / 2).toFloat()
        y = ((row + 1) * size - size / 2).toFloat()
    }

    fun equalsStatus(that: RoadPoint): Boolean {
        if (isNeutralState(that.status)) {
            return true
        }
        return this.status == that.status
    }

    companion object {

        private val pool by lazy {
            Pools2.SynchronizedPool<RoadPoint>(720)
        }

        private val list by lazy {
            Collections.synchronizedList(ArrayList<RoadPoint>())
        }

        @JvmStatic
        fun obtain(): RoadPoint = (pool.acquire() ?: RoadPoint())

        @JvmStatic
        fun release() {
            synchronized(list) {
                list.forEach {
                    try {
                        pool.release(it.empty())
                    } catch (ignored: Exception) {
                    }
                }
                list.clear()
            }
        }
    }
}