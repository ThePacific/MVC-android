package com.square.data

import com.square.data.http.DataHttpModule
import com.square.data.http.createTestRetrofit
import com.square.data.http.entities.ApiToken
import com.square.data.http.okhttp3.retrofitMap
import com.square.data.http.okhttp3.toSource
import com.square.data.http.service.ApiService
import com.square.guava.domain.CoreJdk
import com.square.guava.domain.Source
import com.square.guava.extension.exhaustive
import com.squareup.moshi.Moshi
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(MockitoJUnitRunner::class)
class ApiServiceTest {

    private lateinit var retrofit: Retrofit
    private lateinit var apiService: ApiService
    private lateinit var moshi: Moshi

    @BeforeTest
    fun beforeTest() {
        retrofit = createTestRetrofit(1)
        apiService = retrofit.create(ApiService::class.java)
        moshi = DataHttpModule().provideMoshi()

        runBlocking {
            val req = ApiToken.Req(
                "androidtest1",
                "bob",
                "111111",
                3
            )
            val source = apiService.apiToken(moshi.retrofitMap(req))
                .execute()
                .toSource()
            when (source) {
                is Source.Success -> {
                    CoreJdk.token1 = source.data
                }
                is Source.Error -> {
                    source.e.printStackTrace()
                }
            }.exhaustive
        }
    }

    @Test
    fun test_apiGame() {
        runBlocking {
            when (val source = apiService.apiGame().execute().toSource()) {
                is Source.Success -> {
                    // source.data.forEach { println(it.name) }
                }
                is Source.Error -> source.e.printStackTrace()
            }.exhaustive
        }
    }

    @Test
    fun test_apiNewGame() {
        runBlocking {
            when (val source = apiService.apiNewGame().execute().toSource()) {
                is Source.Success -> {
                    // source.data.forEach { println(it.name) }
                }
                is Source.Error -> source.e.printStackTrace()
            }.exhaustive
        }
    }
}