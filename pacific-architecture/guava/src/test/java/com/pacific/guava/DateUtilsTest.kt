package com.pacific.guava

import com.pacific.guava.date.DateUtils
import org.junit.Test

class DateUtilsTest {

    @Test
    fun date_isCorrect() {
        println(DateUtils.msToString(104701 * 1000))
    }
}
