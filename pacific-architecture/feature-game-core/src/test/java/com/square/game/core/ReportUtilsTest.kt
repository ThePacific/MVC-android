package com.square.game.core

import com.square.game.core.base.SSS1
import com.square.game.core.base.SSS2
import com.square.game.core.base.numbers0To9
import com.square.game.core.driver.instant.InstantDriver
import com.square.game.core.driver.report.getReport
import com.square.game.core.entities.GResult
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class ReportUtilsTest {

    @Test
    fun getReport_isCorrect() {
        val results = createResults()
        val points = getReport(
            results,
            numbers0To9,
            intArrayOf(8, 11, 14, 17, 20)
        )
        points[0].apply {
            assertTrue(3 == x)
            assertTrue(0 == y)
        }

        points[4].apply {
            assertTrue(0 == x)
            assertTrue(3 == y)
        }

        points[8].apply {
            assertTrue(3 == x)
            assertTrue(0 == y)
        }
        points[5].apply {
            assertTrue(1 == x)
            assertTrue(2 == y)
        }
        points[9].apply {
            assertTrue(2 == x)
            assertTrue(0 == y)
        }


        val pointsList = getReport(
            results,
            arrayOf(
                intArrayOf(SSS1, SSS2),
                intArrayOf(SSS1, SSS2)
            ),
            intArrayOf(9, 10)
        )

        pointsList[0].apply {
            this[0].apply {
                assertTrue(1 == x)
                assertTrue(2 == y)
            }
            this[1].apply {
                assertTrue(2 == x)
                assertTrue(0 == y)
            }
        }

        pointsList[1].apply {
            this[0].apply {
                assertTrue(0 == x)
                assertTrue(3 == y)
            }
            this[1].apply {
                assertTrue(3 == x)
                assertTrue(0 == y)
            }
        }
    }

    private fun createResults(): ArrayList<GResult> {
        val list = ArrayList<GResult>()
        list.add(
            GResult.acquire().apply {
                setNumbers(intArrayOf(0, 8, 1, 9, 9))
                InstantDriver.applyStates(this)
            }
        )
        list.add(
            GResult.acquire().apply {
                setNumbers(intArrayOf(2, 0, 8, 0, 1))
                InstantDriver.applyStates(this)
            }
        )
        list.add(
            GResult.acquire().apply {
                setNumbers(intArrayOf(6, 2, 5, 1, 8))
                InstantDriver.applyStates(this)
            }
        )
        list.reverse()
        return list
    }
}
