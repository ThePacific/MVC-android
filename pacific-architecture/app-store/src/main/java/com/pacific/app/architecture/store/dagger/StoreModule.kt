package com.pacific.app.architecture.store.dagger

import com.pacific.app.architecture.store.http.service.DataService
import com.pacific.guava.data.DataModule
import com.pacific.guava.data.SimpleDataModule
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Module
class StoreModule : DataModule {

    private val delegate = SimpleDataModule()

    @Singleton
    @Provides
    override fun providePoorX509TrustManager(): X509TrustManager {
        return delegate.providePoorX509TrustManager()
    }

    @Singleton
    @Provides
    override fun providePoorSSLContext(x509TrustManager: X509TrustManager): SSLContext {
        return delegate.providePoorSSLContext(x509TrustManager)
    }

    @Singleton
    @Provides
    override fun provideHttpLoggingInterceptorLogger(): HttpLoggingInterceptor.Logger {
        return delegate.provideHttpLoggingInterceptorLogger()
    }

    @Singleton
    @Provides
    override fun provideHttpLoggingInterceptor(httpLoggingInterceptorLogger: HttpLoggingInterceptor.Logger): HttpLoggingInterceptor {
        return delegate.provideHttpLoggingInterceptor(httpLoggingInterceptorLogger)
    }

    @Singleton
    @Provides
    override fun provideOkHttpClient(
        x509TrustManager: X509TrustManager,
        sslContext: SSLContext,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        httpLoggingInterceptorLogger: HttpLoggingInterceptor.Logger
    ): OkHttpClient {
        return delegate.provideOkHttpClient(
            x509TrustManager,
            sslContext,
            httpLoggingInterceptor,
            httpLoggingInterceptorLogger
        )
    }

    @Singleton
    @Provides
    override fun provideJson(): Moshi {
        return delegate.provideJson()
    }

    @Singleton
    @Provides
    override fun provideRetrofit(okHttpClient: OkHttpClient, json: Moshi): Retrofit {
        return delegate.provideRetrofit(okHttpClient, json)
    }

    @Singleton
    @Provides
    fun provideDataService(retrofit: Retrofit): DataService {
        return retrofit.create(DataService::class.java)
    }
}