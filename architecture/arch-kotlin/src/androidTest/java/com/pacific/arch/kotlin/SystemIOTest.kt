package com.pacific.arch.kotlin

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.pacific.arch.kotlin.data.addSeparator
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class SystemIOTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.pacific.arch_demo", appContext.packageName)
    }

    @Test
    fun testAddSeparator() {
        val dir = "example"
        assertEquals("example" + File.separator, addSeparator(dir))
    }
}