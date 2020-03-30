package com.pacific.data.http

import com.pacific.guava.domain.Values
import com.pacific.data.http.okhttp3.ApiConverterFactory
import com.pacific.data.http.okhttp3.HeadersInterceptor
import com.pacific.data.http.okhttp3.HostInterceptor
import com.pacific.data.http.okhttp3.WarnIfSlowInterceptor
import com.pacific.data.http.service.ApiService
import com.pacific.data.http.service.RxJavaApiService
import com.pacific.data.http.service.SuspendApiService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class DataHttpModule {

    @Provides
    @Singleton
    fun providePoorX509TrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }
        }
    }

    @Provides
    @Singleton
    fun providePoorSSLContext(x509TrustManager: X509TrustManager): SSLContext {
        try {
            return Platform.get().newSSLContext().apply {
                init(null, arrayOf<TrustManager>(x509TrustManager), SecureRandom())
            }
        } catch (e: NoSuchAlgorithmException) {
            throw e
        } catch (e: KeyManagementException) {
            throw e
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptorLogger(): HttpLoggingInterceptor.Logger {
        return object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                println("OkHttp $message")
            }
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(
        httpLoggingInterceptorLogger: HttpLoggingInterceptor.Logger
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(httpLoggingInterceptorLogger).apply {
            this.level = if (Values.isDebug) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        x509TrustManager: X509TrustManager,
        sslContext: SSLContext,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        httpLoggingInterceptorLogger: HttpLoggingInterceptor.Logger
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HostInterceptor())
            .addInterceptor(HeadersInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(WarnIfSlowInterceptor())
            // See https://github.com/square/okhttp/issues/5464
            .eventListenerFactory(LoggingEventListener.Factory(httpLoggingInterceptorLogger))
            .sslSocketFactory(sslContext.socketFactory, x509TrustManager)
            .hostnameVerifier(HostnameVerifier { _, _ -> true })
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.google.com/")
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ApiConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRxJavaApiService(retrofit: Retrofit): RxJavaApiService {
        return retrofit.create(RxJavaApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideSuspendApiService(retrofit: Retrofit): SuspendApiService {
        return retrofit.create(SuspendApiService::class.java)
    }
}