package com.pacific.arch.kotlin

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RxTest {
    @Rule
    public val mActivityRule = ActivityTestRule<TestActivity>(TestActivity::class.java)

    @Before
    fun setUp() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val intent = Intent()
        intent.setClass(appContext, TestActivity::class.java)
        appContext.startActivity(intent)

    }
}