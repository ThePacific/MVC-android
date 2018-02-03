package com.pacific.example

import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.Excludes
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.pacific.arch.example.App
import java.io.InputStream

@Excludes(OkHttpLibraryGlideModule::class)
@GlideModule
class NewOkHttpLibraryGlideModule : AppGlideModule() {
    init {
    }

    override fun isManifestParsingEnabled() = false

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        Log.e("1_______", App.instance!!.okHttpClient.toString())
        registry.replace(GlideUrl::class.java, InputStream::class.java, OkHttpUrlLoader.Factory(App.instance!!.okHttpClient))
    }
}