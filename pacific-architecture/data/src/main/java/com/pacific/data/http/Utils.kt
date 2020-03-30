package com.pacific.data.http

import com.pacific.guava.domain.Values
import retrofit2.Retrofit

fun createTestRetrofit(urls: Array<String>): Retrofit {
    Values.isDebug = true
    Values.baseUrl1 = urls[0]
    Values.baseUrl2 = urls[1]
    Values.baseUrl3 = urls[2]
    val dataModule = DataHttpModule()
    val moshi = dataModule.provideMoshi()
    val poorX509TrustManager = dataModule.providePoorX509TrustManager()
    val poorSSLContext = dataModule.providePoorSSLContext(poorX509TrustManager)
    val httpLoggingInterceptorLogger = dataModule.provideHttpLoggingInterceptorLogger()
    val httpLoggingInterceptor = dataModule.provideHttpLoggingInterceptor(
        httpLoggingInterceptorLogger
    )
    val okHttpClient = dataModule.provideOkHttpClient(
        poorX509TrustManager,
        poorSSLContext,
        httpLoggingInterceptor,
        httpLoggingInterceptorLogger
    )
    return dataModule.provideRetrofit(okHttpClient, moshi)
}