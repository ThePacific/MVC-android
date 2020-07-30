package com.pacific.data

import com.google.common.truth.Truth
import com.pacific.data.test.TestAppDatabase
import com.pacific.data.test.TestPrefsManager
import com.pacific.data.test.setupTest
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(MockitoJUnitRunner::class)
class DataTest {

    @BeforeTest
    fun beforeTest() = setupTest()

    @Test
    fun test() {
        Truth.assertThat(dataComponent).isNotNull()
        Truth.assertThat(dataComponent.appPrefsManager()).isEqualTo(TestPrefsManager)
        Truth.assertThat(dataComponent.appDatabase()).isEqualTo(TestAppDatabase)
    }
}
