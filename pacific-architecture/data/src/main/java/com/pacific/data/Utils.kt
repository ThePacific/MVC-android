package com.pacific.data

import com.pacific.guava.Guava
import com.pacific.guava.domain.SourceException
import retrofit2.Retrofit

lateinit var dataComponent: DataComponent

const val GOOGLE = "https://www.google.com/"

val hosts = mapOf(
    100 to "http://101.36.181.108:8001",
    200 to "http://101.36.181.108:8000",
    300 to "http://101.36.181.108:8000"
)

val sourceException404 = SourceException("404", 404)
val sourceException403 = SourceException("403", 403)

fun String.isLogin(): Boolean = isNotEmpty() && length > 8

fun Throwable.toSourceException(code: Int = -1): SourceException {
    return if (this is SourceException) {
        this
    } else {
        SourceException(this.message, this.cause, code)
    }
}

fun Throwable.is404(): Boolean = this == sourceException404

fun Throwable.is403(): Boolean = this == sourceException403

fun isLogin1(): Boolean = dataComponent.appPrefsManager().getToken1().isLogin()

fun isLogin2(): Boolean = dataComponent.appPrefsManager().getToken2().isLogin()

fun isLogin3(): Boolean = dataComponent.appPrefsManager().getToken3().isLogin()

fun createTestRetrofit(): Retrofit {
    Guava.isDebug = true

    val dataModule = DataModule()
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
    return dataModule.provideRetrofit(okHttpClient, dataModule.provideMoshi())
}