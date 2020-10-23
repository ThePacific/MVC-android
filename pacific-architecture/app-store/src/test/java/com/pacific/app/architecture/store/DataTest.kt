package com.pacific.app.architecture.store

import com.google.common.truth.Truth
import com.pacific.guava.jvm.Guava
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(MockitoJUnitRunner::class)
class DataTest {

    @BeforeTest
    fun beforeTest() {
        Guava.isDebug = true
        StoreX.setup(TestDatabase, TestContext, TestPrefs)
    }

    @Test
    fun test() {
        Truth.assertThat(StoreX.component).isNotNull()
        Truth.assertThat(StoreX.component.platformPrefs()).isEqualTo(TestPrefs)
        Truth.assertThat(StoreX.component.platformDatabase()).isEqualTo(TestDatabase)
        Truth.assertThat(StoreX.component.platformContext()).isEqualTo(TestContext)
    }
}
