package com.pacific.app.architecture.data

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
        DataLib.setup(TestContext, TestDatabase, TestPrefsManager)
    }

    @Test
    fun test() {
        Truth.assertThat(DataLib.component).isNotNull()
        Truth.assertThat(DataLib.component.appPrefsManager()).isEqualTo(TestPrefsManager)
        Truth.assertThat(DataLib.component.appDatabase()).isEqualTo(TestDatabase)
        Truth.assertThat(DataLib.component.appContext()).isEqualTo(TestContext)
    }
}
