package com.pacific.example.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.pacific.adapter.RecyclerAdapter
import com.pacific.example.DEBUG
import com.pacific.example.OS_PREFS
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Singleton
@Module(includes = [(AndroidSupportInjectionModule::class), (AppBinder::class)],
        subcomponents = [(GlideComponent::class)])
class AppModule {
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor(
                HttpLoggingInterceptor.Logger {

                })
                .setLevel(if (DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                })
    }

    @Provides
    fun provideX509TrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun getAcceptedIssuers() = arrayOf<X509Certificate>()

            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
            }
        }
    }

    @Provides
    fun provideSSLContext(x509TrustManager: X509TrustManager): SSLContext {
        val sslContext: SSLContext
        try {
            sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf<TrustManager>(x509TrustManager), SecureRandom())
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: KeyManagementException) {
            throw RuntimeException(e)
        }
        return sslContext
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                            x509TrustManager: X509TrustManager,
                            sslContext: SSLContext): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(sslContext.socketFactory, x509TrustManager)
                .hostnameVerifier { _, _ -> true }
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(APP: Application): SharedPreferences {
        return APP.getSharedPreferences(OS_PREFS, Context.MODE_PRIVATE)
    }

    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    fun provideRecyclerAdapter() = RecyclerAdapter()
}