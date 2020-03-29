package com.square.game.core

import com.google.common.truth.Truth
import com.square.game.core.base.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class NumberUtilsTest {

    @Test
    fun numbersUtils_isCorrect() {
        val array = intArrayOf(5, 2, 3, 6, 8, 9)
        assertTrue(array.range(1, 3).size == 2)
        assertTrue(array.range(1, 3)[0] == 2)
        assertTrue(array.range(1, 3)[1] == 3)
        assertTrue(array.range(2, 5).size == 3)
        assertTrue(array.range(2, 5)[0] == 3)
        assertTrue(array.range(2, 5)[1] == 6)
        assertTrue(array.range(2, 5)[2] == 8)

        assertTrue(array.left(2).size == 2)
        assertTrue(array.left(2)[0] == 5)
        assertTrue(array.left(2)[1] == 2)
        assertTrue(array.left(4).size == 4)
        assertTrue(array.left(4)[0] == 5)
        assertTrue(array.left(4)[1] == 2)
        assertTrue(array.left(4)[2] == 3)
        assertTrue(array.left(4)[3] == 6)

        assertTrue(array.right(2).size == 2)
        assertTrue(array.right(2)[0] == 8)
        assertTrue(array.right(2)[1] == 9)
        assertTrue(array.right(4).size == 4)
        assertTrue(array.right(4)[0] == 3)
        assertTrue(array.right(4)[1] == 6)
        assertTrue(array.right(4)[2] == 8)
        assertTrue(array.right(4)[3] == 9)

        Truth.assertThat(intArrayOf(5, 2, 3, 6, 8, 9, 5, 2, 5, 2).repeatCount())
            .isEqualTo(2)
        Truth.assertThat(intArrayOf(5, 2, 3, 6, 8, 9, 5, 5, 5, 5).repeatCountState())
            .isEqualTo(5)
        Truth.assertThat(intArrayOf(5, 2, 3, 6, 8, 9).repeatCountState())
            .isEqualTo(1)
    }
}
