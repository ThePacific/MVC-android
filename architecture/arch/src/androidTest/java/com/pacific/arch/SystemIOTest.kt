package com.pacific.arch

import android.support.test.runner.AndroidJUnit4
import com.pacific.arch.data.addSeparator
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class SystemIOTest {
    @Test
    fun testAddSeparator() {
        assertEquals("example" + File.separator, addSeparator("example"))
        assertEquals("example" + File.separator, addSeparator("example" + File.separator))
    }
}