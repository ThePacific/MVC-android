package com.pacific.guava.data

import com.pacific.guava.jvm.Guava
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
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

fun createPoorSSLOkHttpClient(loggerTag: String): OkHttpClient {
    val httpsModule = SimpleDataModule()
    val poorX509TrustManager = httpsModule.providePoorX509TrustManager()
    val poorSSLContext = httpsModule.providePoorSSLContext(poorX509TrustManager)
    val httpLoggingInterceptorLogger = object : HttpLoggingInterceptor.Logger {
        override fun log(message: String) {
            Guava.timber.d(loggerTag, message)
        }
    }
    val httpLoggingInterceptor = httpsModule.provideHttpLoggingInterceptor(
        httpLoggingInterceptorLogger
    )
    return OkHttpClient().newBuilder()
        .addInterceptor(httpLoggingInterceptor)
        .eventListenerFactory(LoggingEventListener.Factory(httpLoggingInterceptorLogger))
        .sslSocketFactory(poorSSLContext.socketFactory, poorX509TrustManager)
        .hostnameVerifier(HostnameVerifier { _, _ -> true })
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}