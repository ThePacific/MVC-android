package com.pacific.data

import com.pacific.data.db.AppDatabase
import com.pacific.data.files.AppPrefsManager
import com.pacific.data.http.service.DataService
import com.pacific.data.repository.UserRepository
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import javax.inject.Named
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

interface DataComponent {

    fun poorX509TrustManager(): X509TrustManager

    fun poorSSLContext(): SSLContext

    fun httpLoggingInterceptorLogger(): HttpLoggingInterceptor.Logger

    fun httpLoggingInterceptor(): HttpLoggingInterceptor

    fun okHttpClient(): OkHttpClient

    fun moshi(): Moshi

    fun retrofit(): Retrofit

    fun dataService(): DataService

    fun userRepository(): UserRepository

    fun appDatabase(): AppDatabase

    fun appPrefsManager(): AppPrefsManager

    fun okHttpCache(): Cache

    @Named("appExternalCacheDir")
    fun appExternalCacheDir(): File
}