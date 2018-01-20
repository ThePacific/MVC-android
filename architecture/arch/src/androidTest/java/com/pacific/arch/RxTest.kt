package com.pacific.arch

import android.support.test.InstrumentationRegistry
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.pacific.arch.rx.ObservableUtil
import io.reactivex.Observable
import org.junit.Assert
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
        var mapThreadId = -1L
        Observable.just(100)
                .map { mapThreadId = Thread.currentThread().id }
                .compose(ObservableUtil.io())
                .subscribe({ Assert.assertFalse(mapThreadId == Thread.currentThread().id) })
    }
}