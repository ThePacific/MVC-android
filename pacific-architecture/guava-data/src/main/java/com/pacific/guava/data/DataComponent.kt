package com.pacific.guava.data

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

interface DataComponent {

    fun poorX509TrustManager(): X509TrustManager

    fun poorSSLContext(): SSLContext

    fun httpLoggingInterceptorLogger(): HttpLoggingInterceptor.Logger

    fun httpLoggingInterceptor(): HttpLoggingInterceptor

    fun okHttpClient(): OkHttpClient

    fun json(): Moshi

    fun retrofit(): Retrofit

    fun platformContext(): PlatformContext
}