package com.square.data.http.okhttp3

import com.square.guava.domain.CoreJdk
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HostSelectionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        require(CoreJdk.baseUrl.isNotEmpty())

        var request = chain.request()
        val baseUrl = CoreJdk.baseUrl.toHttpUrlOrNull()!!
        val newUrl = request.url.newBuilder()
                .host(baseUrl.host)
                .scheme(baseUrl.scheme)
                .port(baseUrl.port)
                .build()
        request = request.newBuilder()
                .url(newUrl)
                .build()

        return chain.proceed(request)
    }
}

