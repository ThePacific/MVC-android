package com.square.game.core

import com.square.game.core.driver.mark6.*
import com.square.guava.date.toInstant1
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class Mark6Test {

    @Test
    fun utils_test() {
        assertEquals(ZODIAC_PIG, zodiacOf49(System.currentTimeMillis()))
        assertEquals(
            ZODIAC_DOG,
            zodiacOf49(toInstant1("2018-10-01 00:00:00").toEpochMilli())
        )
        zodiacNumbers.forEach {
            println("${Mark6Driver.zodiacStateName(it.key)} ${it.value.joinToString(",")}")
        }
        println("== == ==")
        preZodiacNumbers.forEach {
            println("${Mark6Driver.zodiacStateName(it.key)} ${it.value.joinToString(",")}")
        }
    }
}
