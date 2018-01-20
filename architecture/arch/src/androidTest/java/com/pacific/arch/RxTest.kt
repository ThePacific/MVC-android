package com.pacific.arch

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RxTest {

    @JvmField
    @Rule
    public val mActivityRule = ActivityTestRule<TestActivity>(TestActivity::class.java)

    val instrumentation = InstrumentationRegistry.getInstrumentation()

    @Before
    fun setUp() {
    }

    @Test
    fun testSchedulers() {

    }
}