package com.square.data.http.okhttp3

import com.square.guava.domain.CoreJdk
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HeadersInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("token", CoreJdk.token1)
            .addHeader("device", "3")
            .build()
        return chain.proceed(request)
    }
}
