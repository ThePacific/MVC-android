package com.pacific.app.data.http.okhttp3

import com.pacific.app.data.dataComponent
import com.pacific.app.data.hosts
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class HostSelectionInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val flavorId = dataComponent.appPrefsManager().getFlavorId()
        val selectedHttpUrl = hosts.getValue(flavorId).toHttpUrl()
        val realHttpUrl = request.url.newBuilder()
            .host(selectedHttpUrl.host)
            .scheme(selectedHttpUrl.scheme)
            .port(selectedHttpUrl.port)
            .build()

        return chain.proceed(request.newBuilder().url(realHttpUrl).build())
    }
}

