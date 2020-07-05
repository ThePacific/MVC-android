package com.pacific.guava

import com.pacific.guava.date.ago
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.threeten.bp.Instant

@RunWith(MockitoJUnitRunner::class)
class GuavaTest {

    @Test
    fun test_ago() {
        val start: Instant = Instant.parse("2017-10-03T10:15:30.00Z")
        val end: Instant = Instant.parse("2017-10-03T10:16:30.00Z")
        println(ago(start, end))
    }
}
