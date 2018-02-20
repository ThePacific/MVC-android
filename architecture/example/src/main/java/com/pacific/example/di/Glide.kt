package com.pacific.example.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.pacific.arch.example.App
import dagger.Module
import dagger.Subcomponent
import okhttp3.OkHttpClient
import timber.log.Timber
import java.io.InputStream
import javax.inject.Inject

@GlideModule
@Excludes(OkHttpLibraryGlideModule::class)
class NewOkHttpLibraryGlideModule : AppGlideModule() {
    @Inject
    lateinit var okHttpClient: OkHttpClient

    init {
        App.instance!!.appComponent().glideComponentBuilder().build().inject(this)
    }

    override fun isManifestParsingEnabled() = false

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        Timber.e("this client is %s", okHttpClient)
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }
}

@Subcomponent(modules = [(GlideDaggerModule::class)])
interface GlideComponent {
    fun inject(okHttpLibraryGlideModule: NewOkHttpLibraryGlideModule)

    @Subcomponent.Builder
    interface Builder {
        fun build(): GlideComponent
    }
}

@Module
class GlideDaggerModule