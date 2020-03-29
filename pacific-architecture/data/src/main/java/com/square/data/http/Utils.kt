package com.square.data.http

import com.square.guava.domain.CoreJdk
import com.square.guava.domain.unsupportedOperationException
import retrofit2.Retrofit

fun createTestRetrofit(flag: Int): Retrofit {
    val urls: Pair<String, String> = when (flag) {
        1 -> Pair(
            "https://dev-login.bobcp.vip:8007",
            "https://dev-bob-web.emkcp.com"
        )
        2 -> Pair(
            "https://fat-login.emkcp.com:8009",
            "https://fat-bob-wap.emkcp.com"
        )
        3 -> Pair(
            "https://uat-login.bobcp.vip:8005",
            "https://uat-wap.bobcp.vip"
        )
        else -> unsupportedOperationException()
    }
    CoreJdk.isDebug = true
    CoreJdk.baseUrl = urls.first
    
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