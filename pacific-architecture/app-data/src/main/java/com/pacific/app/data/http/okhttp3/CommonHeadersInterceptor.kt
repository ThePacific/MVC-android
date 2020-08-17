package com.pacific.app.data.http.okhttp3

import com.pacific.app.data.DataLib
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class CommonHeadersInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val prefsManager = DataLib.component.appPrefsManager()
        val newRequest = request.newBuilder()
            .addHeader("token", prefsManager.getToken1())
            .addHeader("userId", prefsManager.getUserId().toString())
            .addHeader("deviceId", prefsManager.getDeviceId())
            .addHeader("platform", "android")
            .build()
        return chain.proceed(newRequest)
    }
}
