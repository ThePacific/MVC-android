package com.square.domain

import com.square.guava.domain.CoreJdk
import org.junit.Test
import kotlin.test.assertEquals

class CoreJdkTest {

    @Test
    fun cache_isCorrect() {
        val player = Player("Barry")
        CoreJdk.put("player", player)
        assertEquals(player, CoreJdk.get("player"))
        assertEquals("Barry", CoreJdk.get<Player>("player")?.name)
        CoreJdk.remove("player")
        assertEquals(null, CoreJdk.get("player"))

        CoreJdk.put("first name", "Barry")
        CoreJdk.put("last name", "Allen")
        assertEquals(CoreJdk.get("first name"), "Barry")
        assertEquals(CoreJdk.get("last name"), "Allen")
        CoreJdk.remove("first name")
        CoreJdk.remove("last name")
        assertEquals(CoreJdk.get("first name"), null)
        assertEquals(CoreJdk.get("last name"), null)

        CoreJdk.put("player", player)
        assertEquals(player, CoreJdk.get("player"))
        assertEquals(player, CoreJdk.take("player"))
        assertEquals(null, CoreJdk.get("player"))
    }
}
