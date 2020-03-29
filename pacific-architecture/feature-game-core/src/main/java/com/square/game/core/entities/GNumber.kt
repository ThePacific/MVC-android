package com.square.game.core.entities

import com.square.guava.domain.Pools2
import java.util.*
import kotlin.collections.ArrayList

class GNumber private constructor() {

    val amount: Double = 1.000000

    var playId: Long = -1
    var index: Int = -1
    var name: String = ""
    var number: Int = -1
    var backendNumber: String = ""
    var odds1: Double = 0.000000
    var odds2: Double = 0.000000
    var odds3: Double = 0.000000
    var miss: Int = 0
    var hot: Int = 0
    var stateAmount: Double = 0.000000
    var state: Int = -1
    var factor: Long = 1

    var extra1: Int = -1
    var extra2: Int = -1
    var extra3: String = ""
    var extra4: Double = 0.000000

    private fun empty(): GNumber {
        playId = -1L
        index = -1
        name = ""
        number = -1
        backendNumber = ""
        odds1 = 0.000000
        odds2 = 0.000000
        odds3 = 0.000000
        miss = 0
        hot = 0
        stateAmount = 0.000000
        state = -1
        factor = 1L

        extra1 = -1
        extra2 = -1
        extra3 = ""
        extra4 = 0.000000
        return this
    }

    override fun toString(): String {
        return "GNumber($name, $backendNumber, $index)"
    }

    companion object {

        private val pool by lazy {
            Pools2.SynchronizedPool<GNumber>(1024)
        }

        private val list by lazy {
            Collections.synchronizedList(ArrayList<GNumber>())
        }

        @JvmStatic
        fun obtain(): GNumber = (pool.acquire() ?: GNumber())

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