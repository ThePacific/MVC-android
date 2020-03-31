package com.pacific.data

import com.pacific.data.http.DataHttpModule
import com.pacific.guava.GOOGLE
import com.pacific.guava.domain.Values
import retrofit2.Retrofit

fun setupTestSuite(): Retrofit {
    Values.isDebug = true
    Values.baseUrl1 = GOOGLE
    Values.baseUrl2 = GOOGLE
    Values.baseUrl3 = GOOGLE
    
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