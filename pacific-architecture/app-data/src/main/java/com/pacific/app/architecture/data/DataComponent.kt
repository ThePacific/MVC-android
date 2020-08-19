package com.pacific.app.architecture.data

import com.pacific.app.architecture.data.base.AppContext
import com.pacific.app.architecture.data.db.AppDatabase
import com.pacific.app.architecture.data.file.AppPrefsManager
import com.pacific.app.architecture.data.http.service.DataService
import com.pacific.app.architecture.data.repository.UserRepository
import com.squareup.moshi.Moshi
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Component(
    modules = [
        DataModule::class
    ]
)
@Singleton
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

    fun appContext(): AppContext

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance appContext: AppContext,
            @BindsInstance appDatabase: AppDatabase,
            @BindsInstance appPrefsManager: AppPrefsManager
        ): DataComponent
    }
}