package com.pacific.data.http.okhttp3

import com.pacific.data.dataComponent
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CommonHeadersInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val prefsManager = dataComponent.appPrefsManager()
        val newRequest = request.newBuilder()
            .addHeader("token", prefsManager.getToken1())
            .addHeader("userId", prefsManager.getUserId().toString())
            .addHeader("deviceId", prefsManager.getDeviceId())
            .addHeader("platform", "android")
            .build()
        return chain.proceed(newRequest)
    }
}
