package com.pacific.data.http.okhttp3

import com.pacific.data.http.DataHttpModule
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

fun cancelOkHttp3Request(client: OkHttpClient, tag: Any) {
    client.dispatcher.queuedCalls()
        .filter { tag == it.request().tag() }
        .forEach { it.cancel() }
    client.dispatcher.runningCalls()
        .filter { tag == it.request().tag() }
        .forEach { it.cancel() }
}

fun cancelOkHttp3Request(client: OkHttpClient) {
    client.dispatcher.queuedCalls()
        .forEach { it.cancel() }
    client.dispatcher.runningCalls()
        .forEach { it.cancel() }
}

fun createPoorSSLOkHttpClient(): OkHttpClient {
    val dataModule = DataHttpModule()
    val poorX509TrustManager = dataModule.providePoorX509TrustManager()
    val poorSSLContext = dataModule.providePoorSSLContext(poorX509TrustManager)
    return OkHttpClient.Builder()
        .sslSocketFactory(poorSSLContext.socketFactory, poorX509TrustManager)
        .hostnameVerifier(HostnameVerifier { _, _ -> true })
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}