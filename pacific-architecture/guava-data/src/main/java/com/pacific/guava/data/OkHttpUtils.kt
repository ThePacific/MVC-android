package com.pacific.guava.data

import com.pacific.guava.jvm.Guava
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

/**
 * 取消tag对应的网络请求
 */
fun cancelOkHttp3Request(client: OkHttpClient, tag: Any) {
    client.dispatcher.queuedCalls()
        .filter { tag == it.request().tag() }
        .forEach { it.cancel() }
    client.dispatcher.runningCalls()
        .filter { tag == it.request().tag() }
        .forEach { it.cancel() }
}

/**
 * 取消所有网络请求
 */
fun cancelOkHttp3Request(client: OkHttpClient) {
    client.dispatcher.queuedCalls()
        .forEach { it.cancel() }
    client.dispatcher.runningCalls()
        .forEach { it.cancel() }
}

/**
 * 创建OkHttpClient，忽略证书验证
 */
fun createPoorSSLOkHttpClient(loggerTag: String): OkHttpClient {
    val httpsModule = SimpleDataModule()
    val poorX509TrustManager = httpsModule.providePoorX509TrustManager()
    val poorSSLContext = httpsModule.providePoorSSLContext(poorX509TrustManager)
    val httpLoggingInterceptorLogger = HttpLoggingInterceptor.Logger { message ->
        Guava.timber.d(loggerTag, message)
    }
    val httpLoggingInterceptor = httpsModule.provideHttpLoggingInterceptor(
        httpLoggingInterceptorLogger
    )
    return OkHttpClient().newBuilder()
        .addInterceptor(httpLoggingInterceptor)
        .sslSocketFactory(poorSSLContext.socketFactory, poorX509TrustManager)
        .hostnameVerifier(HostnameVerifier { _, _ -> true })
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}