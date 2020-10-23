package com.pacific.app.architecture.store.http.okhttp3

import com.pacific.app.architecture.store.StoreX
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class CommonHeadersInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val prefsManager = StoreX.component.platformPrefs()
        val newRequest = request.newBuilder()
            .addHeader("token", prefsManager.token)
            .addHeader("userId", prefsManager.userId)
            .addHeader("deviceId", prefsManager.deviceId)
            .addHeader("platform", "android")
            .build()
        return chain.proceed(newRequest)
    }
}
