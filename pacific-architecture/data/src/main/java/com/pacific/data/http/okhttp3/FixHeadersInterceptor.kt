package com.pacific.data.http.okhttp3

import com.pacific.guava.domain.Values
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class FixHeadersInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("token", Values.token1)
            .addHeader("device", "android")
            .build()
        return chain.proceed(request)
    }
}
