package com.pacific.data.http.okhttp3

import com.pacific.guava.domain.Values
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HostInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        require(Values.baseUrl1.isNotEmpty())
        var request = chain.request()
        val baseHttpUrl = Values.baseUrl1.toHttpUrl()
        val newHttpUrl = request.url.newBuilder()
            .host(baseHttpUrl.host)
            .scheme(baseHttpUrl.scheme)
            .port(baseHttpUrl.port)
            .build()
        request = request.newBuilder()
            .url(newHttpUrl)
            .build()
        return chain.proceed(request)
    }
}

