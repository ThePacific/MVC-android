package com.pacific.guava.data

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

interface DataModule {

    fun providePoorX509TrustManager(): X509TrustManager

    fun providePoorSSLContext(x509TrustManager: X509TrustManager): SSLContext

    fun provideHttpLoggingInterceptorLogger(): HttpLoggingInterceptor.Logger

    fun provideHttpLoggingInterceptor(
        httpLoggingInterceptorLogger: HttpLoggingInterceptor.Logger
    ): HttpLoggingInterceptor

    fun provideOkHttpClient(
        x509TrustManager: X509TrustManager,
        sslContext: SSLContext,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        httpLoggingInterceptorLogger: HttpLoggingInterceptor.Logger
    ): OkHttpClient

    fun provideJson(): Moshi

    fun provideRetrofit(okHttpClient: OkHttpClient, json: Moshi): Retrofit
}