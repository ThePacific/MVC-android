package com.pacific.data.http.okhttp3

import com.pacific.data.ALPHA
import com.pacific.data.BETA
import com.pacific.data.RELEASE
import com.pacific.data.dataComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HostSelectionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val dynamicHttpUrl = dynamicHttpUrl()
        val realHttpUrl = request.url.newBuilder()
            .host(dynamicHttpUrl.host)
            .scheme(dynamicHttpUrl.scheme)
            .port(dynamicHttpUrl.port)
            .build()

        return chain.proceed(request.newBuilder().url(realHttpUrl).build())
    }

    private fun dynamicHttpUrl(): HttpUrl {
        val host = when (dataComponent.appPrefsManager().getFlavorId()) {
            ALPHA -> "http://101.36.181.108:8001"
            BETA -> "http://101.36.181.108:8000"
            RELEASE -> "http://101.36.181.108:8000"
            else -> throw IllegalStateException("No flavorId")
        }
        return host.toHttpUrl()
    }
}

