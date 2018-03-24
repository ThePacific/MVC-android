package com.pacific.example.di

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pacific.example.service.LocalSocketService
import com.pacific.example.service.RemoteSocketService
import com.pacific.example.App
import com.pacific.arch.presentation.ViewModelFactory
import com.pacific.arch.presentation.ViewModelKey
import com.pacific.example.common.DEBUG_APP
import com.pacific.example.common.DEBUG_BASE_URL
import com.pacific.example.common.OS_PREFS
import com.pacific.example.common.RELEASE_BASE_URL
import com.pacific.example.data.SystemDatabase
import com.pacific.example.data.WebService
import com.pacific.example.feature.about.AboutActivity
import com.pacific.example.feature.book.BookFragment
import com.pacific.example.feature.book.BookViewModel
import com.pacific.example.feature.film.FilmFragment
import com.pacific.example.feature.film.FilmViewModel
import com.pacific.example.feature.game.GameFragment
import com.pacific.example.feature.game.GameViewModel
import com.pacific.example.feature.home.HomeFragment
import com.pacific.example.feature.home.HomeViewModel
import com.pacific.example.feature.main.MainActivity
import com.pacific.example.feature.main.MainViewModel
import com.pacific.example.feature.news.NewsFragment
import com.pacific.example.feature.news.NewsViewModel
import com.pacific.example.feature.search.SearchActivity
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
                .addNetworkInterceptor(StethoInterceptor())
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
    fun provideWebService(retrofit: Retrofit): WebService {
        return retrofit.create(WebService::class.java)
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
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(it: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel::class)
    abstract fun bindGameViewModel(it: GameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilmViewModel::class)
    abstract fun bindFilmViewModel(it: FilmViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookViewModel::class)
    abstract fun bindBookViewModel(it: BookViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel::class)
    abstract fun bindNewsViewModel(it: NewsViewModel): ViewModel


    ////Activity binders
    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @ContributesAndroidInjector(modules = [(MainActivityBinder::class)])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun searchActivity(): SearchActivity

    @ContributesAndroidInjector
    abstract fun aboutActivity(): AboutActivity


    ////Global Fragment and FragmentDialog binders

    ////Service binders(Everything is like Activity)
    @ContributesAndroidInjector
    abstract fun localSocketService(): LocalSocketService

    @ContributesAndroidInjector
    abstract fun remoteSocketService(): RemoteSocketService
}


@Module
abstract class MainActivityBinder {
    @ContributesAndroidInjector
    abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun gameFragment(): GameFragment

    @ContributesAndroidInjector
    abstract fun filmFragment(): FilmFragment

    @ContributesAndroidInjector
    abstract fun bookFragment(): BookFragment

    @ContributesAndroidInjector
    abstract fun newFragment(): NewsFragment
}