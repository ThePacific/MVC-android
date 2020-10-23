package com.pacific.guava.android.mvvm

import com.pacific.guava.data.PlatformContext
import java.io.File

class AppContext : PlatformContext {

    override fun getCacheDir(): File = AndroidX.myApp.cacheDir

    override fun getFilesDir(): File = AndroidX.myApp.filesDir

    override fun getDatabasePath(name: String): File {
        return AndroidX.myApp.getDatabasePath(name)!!
    }

    override fun getExternalCacheDir(): File {
        return AndroidX.myApp.externalCacheDir!!
    }

    override fun getExternalFilesDir(type: String?): File {
        return AndroidX.myApp.getExternalFilesDir(type)!!
    }
}