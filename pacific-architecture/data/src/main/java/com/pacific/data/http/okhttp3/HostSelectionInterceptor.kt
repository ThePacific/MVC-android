package com.pacific.data.http.okhttp3

import com.pacific.guava.domain.Values
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HostSelectionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val dynamicHttpUrl = Values.apiUrl1.toHttpUrl()
        val newHttpUrl = request.url.newBuilder()
            .host(dynamicHttpUrl.host)
            .scheme(dynamicHttpUrl.scheme)
            .port(dynamicHttpUrl.port)
            .build()
        request = request.newBuilder()
            .url(newHttpUrl)
            .build()
        return chain.proceed(request)
    }
}

