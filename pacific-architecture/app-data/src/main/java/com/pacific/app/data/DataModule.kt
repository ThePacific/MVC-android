package com.pacific.app.data

import com.pacific.app.data.base.AppContext
import com.pacific.app.data.http.okhttp3.ApiConverterFactory
import com.pacific.app.data.http.okhttp3.CommonHeadersInterceptor
import com.pacific.app.data.http.okhttp3.HostSelectionInterceptor
import com.pacific.app.data.http.okhttp3.WarnIfSlowInterceptor
import com.pacific.app.data.http.service.DataService
import com.pacific.guava.jvm.Guava
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
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
class DataModule {

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
                Guava.timber.d("OkHttp", message)
            }
        }
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(
        httpLoggingInterceptorLogger: HttpLoggingInterceptor.Logger
    ): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(httpLoggingInterceptorLogger).apply {
            this.level = if (Guava.isDebug) {
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
        httpLoggingInterceptorLogger: HttpLoggingInterceptor.Logger,
        appContext: AppContext
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(HostSelectionInterceptor())
            .addInterceptor(CommonHeadersInterceptor())
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(WarnIfSlowInterceptor())
            .eventListenerFactory(LoggingEventListener.Factory(httpLoggingInterceptorLogger))
            .cache(
                Cache(
                    File(appContext.getExternalCacheDir(), "http_manager_disk_cache"),
                    250 * 1024 * 1024
                )
            )
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
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.google.com/")
            .client(okHttpClient)
            .addConverterFactory(ApiConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideDataService(retrofit: Retrofit): DataService {
        return retrofit.create(DataService::class.java)
    }
}