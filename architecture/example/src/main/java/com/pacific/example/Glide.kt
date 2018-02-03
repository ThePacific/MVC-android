package com.pacific.example

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
import java.io.InputStream
import javax.inject.Inject

@Excludes(OkHttpLibraryGlideModule::class)
@GlideModule
class NewOkHttpLibraryGlideModule : AppGlideModule() {
    @Inject
    lateinit var okHttpClient: OkHttpClient

    init {
        App.instance!!.appComponent()
                .glideComponentBuilder()
                .glideModule(GlideDaggerModule())
                .build()
                .inject(this)
    }

    override fun isManifestParsingEnabled() = false

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(okHttpClient))
    }
}


@Subcomponent(modules = [(GlideDaggerModule::class)])
interface GlideComponent {

    fun inject(newOkHttpLibraryGlideModule: NewOkHttpLibraryGlideModule)

    @Subcomponent.Builder
    interface Builder {
        /**optionally,if we don't have any args in GlideConfigModule constructor */
        fun glideModule(glideModule: GlideDaggerModule): Builder

        fun build(): GlideComponent
    }
}


@Module
class GlideDaggerModule