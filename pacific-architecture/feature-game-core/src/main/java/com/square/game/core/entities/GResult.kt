package com.square.game.core.entities

import com.square.game.core.base.ascComparatorInt
import com.square.game.core.base.emptyIntArray
import com.square.guava.domain.Pools2
import java.util.*

class GResult private constructor() {
    private val _states = ArrayList<Int>()
    val states: List<Int> get() = _states

    var gameId: Long = -1L
    var issue: String = ""
    var shortIssue: String = ""
    var issueIndex: Long = -1L
    var time: Long = -1L
    var numbers: IntArray = emptyIntArray
        private set

    var sortedNumbers: IntArray = emptyIntArray
        private set

    fun setNumbers(numbers: IntArray) {
        this.numbers = numbers
        this.sortedNumbers = numbers.sortedWith(ascComparatorInt).toIntArray()
    }

    fun setState(vararg values: Int) {
        values.forEach {
            _states.add(it)
        }
    }

    fun updateState(startIndex: Int, vararg values: Int) {
        for (i in startIndex until values.size) {
            _states[i] = values[i]
        }
    }

    private fun empty(): GResult {
        _states.clear()
        gameId = -1L
        issue = ""
        shortIssue = ""
        issueIndex = -1L
        time = -1L
        numbers = emptyIntArray
        sortedNumbers = emptyIntArray
        return this
    }

    companion object {

        private val pool by lazy {
            Pools2.SynchronizedPool<GResult>(1024)
        }

        private val list by lazy {
            Collections.synchronizedList(ArrayList<GResult>())
        }

        @JvmStatic
        fun obtain(): GResult = (pool.acquire() ?: GResult())

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