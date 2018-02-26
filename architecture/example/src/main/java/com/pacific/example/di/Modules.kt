package com.pacific.example.di

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.pacific.arch.example.App
import com.pacific.arch.example.MainActivity
import com.pacific.arch.presentation.ViewModelFactory
import com.pacific.arch.presentation.ViewModelKey
import com.pacific.example.AppsBottomSheet
import com.pacific.example.MainFragment
import com.pacific.example.MainFragmentViewModel
import com.pacific.example.MainViewModel
import com.pacific.example.common.DEBUG_APP
import com.pacific.example.common.DEBUG_BASE_URL
import com.pacific.example.common.OS_PREFS
import com.pacific.example.common.RELEASE_BASE_URL
import com.pacific.example.data.DataService
import com.pacific.example.data.SystemDatabase
import com.pacific.example.feature.zygote.SplashActivity
import com.pacific.example.feature.zygote.SplashViewModel
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.multibindings.IntoMap
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
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
                    Timber.tag("OkHttp3").i(it)
                })
                .setLevel(if (DEBUG_APP) {
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
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(if (DEBUG_APP) {
                    DEBUG_BASE_URL
                } else {
                    RELEASE_BASE_URL
                })
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }

    @Provides
    @Singleton
    fun provideDataService(retrofit: Retrofit): DataService {
        return retrofit.create(DataService::class.java)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(APP: Application): SharedPreferences {
        return APP.getSharedPreferences(OS_PREFS, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSystemDatabase(app: Application): SystemDatabase {
        return Room.databaseBuilder(app, SystemDatabase::class.java, "delight.db3").build()
    }
}


@Module
abstract class AppBinder {
    ////APP binders
    @Singleton
    @Binds
    abstract fun bindViewModelFactory(it: ViewModelFactory): ViewModelProvider.Factory

    @Singleton
    @Binds
    abstract fun provideApplication(it: App): Application

    @Singleton
    @Binds
    abstract fun provideContext(it: App): Context


    ////ViewModel binders
    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindSplashViewModel(it: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(it: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    abstract fun bindMainFragmentViewModel(it: MainFragmentViewModel): ViewModel


    ////Activity binders
    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [(MainActivityBinder::class)])
    abstract fun mainActivity(): MainActivity


    ////Global Fragment and FragmentDialog binders
}


@Module
abstract class MainActivityBinder {
    @ContributesAndroidInjector(modules = [(MainFragmentBinder::class)])
    abstract fun mainFragment(): MainFragment
}


@Module
abstract class MainFragmentBinder {
    @ContributesAndroidInjector
    abstract fun appsBottomSheet(): AppsBottomSheet
}