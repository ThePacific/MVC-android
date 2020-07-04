package com.pacific.data

import com.pacific.data.http.entities.ApiPlayCount
import com.pacific.data.http.entities.ApiToken
import com.pacific.data.http.service.DataService
import com.pacific.data.repository.PAGE_SIZE
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataTest {

    private val dataService = createTestRetrofit().create(DataService::class.java)
    private val deviceId = "ccadab3e-d4d1-38d8-82ca-2c5db990a19d"

    @Test
    fun test_apiToken() {
        dataService.login(ApiToken.AReq(1, 1, deviceId, "", "")).execute()
    }

    @Test
    fun test_login() {
        dataService.fastLogin(ApiToken.Req(1, deviceId)).execute()
    }

    @Test
    fun test_verifyCode() {
        dataService.verifyCode("18523323311").execute()
    }

    @Test
    fun test_verifyRecommendVideo() {
        runBlocking {
            dataService.quickVideoList(1,20).execute()
        }
    }

    @Test
    fun test_verifyNearbyVideo() {
        runBlocking {
            dataService.quickNearbyVideoList(1.222, 13.33333, 1, PAGE_SIZE).execute()
        }
    }

    @Test
    fun test_verifyPlayCount() {
        runBlocking {
            dataService.playCount(ApiPlayCount.Req(7L)).execute()
        }
    }

    @Test
    fun test_verifyComment() {
        runBlocking {
            dataService.comments(7L).execute()
        }
    }
}
