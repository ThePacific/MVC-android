package com.pacific.guava.data

import com.pacific.guava.jvm.Guava
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.LoggingEventListener
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * 数据层Module dagger实现
 */
class SimpleDataModule : DataModule {

    override fun providePoorX509TrustManager(): X509TrustManager {
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

    override fun providePoorSSLContext(x509TrustManager: X509TrustManager): SSLContext {
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

    override fun provideHttpLoggingInterceptorLogger(): HttpLoggingInterceptor.Logger {
        return HttpLoggingInterceptor.Logger { message ->
            Guava.timber.d("OkHttp", message)
        }
    }

    override fun provideHttpLoggingInterceptor(
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

    override fun provideOkHttpClient(
        x509TrustManager: X509TrustManager,
        sslContext: SSLContext,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        httpLoggingInterceptorLogger: HttpLoggingInterceptor.Logger
    ): OkHttpClient {
        return OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .eventListenerFactory(LoggingEventListener.Factory(httpLoggingInterceptorLogger))
            .sslSocketFactory(sslContext.socketFactory, x509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }

    override fun provideJson(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    override fun provideRetrofit(okHttpClient: OkHttpClient, json: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.google.com/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(json))
            .build()
    }
}