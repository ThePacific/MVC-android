package com.pacific.data.http

import com.pacific.data.http.service.OurApi
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

interface DataHttpComponent {

    fun providePoorX509TrustManager(): X509TrustManager

    fun providePoorSSLContext(): SSLContext

    fun provideHttpLoggingInterceptorLogger(): HttpLoggingInterceptor.Logger

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor

    fun provideOkHttpClient(): OkHttpClient

    fun provideMoshi(): Moshi

    fun provideRetrofit(): Retrofit

    fun provideOurApi(): OurApi
}