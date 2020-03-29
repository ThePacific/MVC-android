package com.square.game.core

import com.square.game.core.base.numbers0To27
import com.square.game.core.base.numbers0To9
import com.square.game.core.base.numbers1To10
import com.square.game.core.base.numbers1To80
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class UtilsTest {

    @Test
    fun numberRange_isCorrect() {
        assertTrue(numbers0To9[0] == 0)
        assertTrue(numbers0To9[9] == 9)
        assertTrue(numbers0To9.size == 10)

        assertTrue(numbers0To27[0] == 0)
        assertTrue(numbers0To27[27] == 27)
        assertTrue(numbers0To27.size == 28)

        assertTrue(numbers1To10[0] == 1)
        assertTrue(numbers1To10[9] == 10)
        assertTrue(numbers1To10.size == 10)

        assertTrue(numbers1To80[0] == 1)
        assertTrue(numbers1To80[79] == 80)
        assertTrue(numbers1To80.size == 80)
    }
}
