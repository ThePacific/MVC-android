package com.pacific.guava.android.mvvm

import com.pacific.guava.data.PlatformContext
import java.io.File
import java.io.InputStream

/**
 * 对android中Context平台抽象实现，主要用于数据层，用于访问设备目录
 */
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

    override fun openAssert(fileName: String): InputStream {
        return AndroidX.myApp.assets.open(fileName)
    }

    override fun openAssert(fileName: String, accessMode: Int): InputStream {
        return AndroidX.myApp.assets.open(fileName, accessMode)
    }
}