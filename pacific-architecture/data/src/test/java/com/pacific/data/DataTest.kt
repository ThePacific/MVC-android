package com.pacific.data

import com.google.common.truth.Truth
import com.pacific.guava.Guava
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(MockitoJUnitRunner::class)
class DataTest {

    @BeforeTest
    fun beforeTest() {
        Guava.isDebug = true
        setupDataModule(TestContext, TestDatabase, TestPrefsManager)
    }

    @Test
    fun test() {
        Truth.assertThat(dataComponent).isNotNull()
        Truth.assertThat(dataComponent.appPrefsManager()).isEqualTo(TestPrefsManager)
        Truth.assertThat(dataComponent.appDatabase()).isEqualTo(TestDatabase)
        Truth.assertThat(dataComponent.appContext()).isEqualTo(TestContext)
    }
}
