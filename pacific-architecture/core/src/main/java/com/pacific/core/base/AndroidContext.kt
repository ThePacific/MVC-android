package com.pacific.core.base

import com.pacific.core.myApp
import com.pacific.data.base.AppContext
import java.io.File

object AndroidContext : AppContext {

    override fun getCacheDir(): File = myApp.cacheDir

    override fun getFilesDir(): File = myApp.filesDir

    override fun getDatabasePath(name: String): File = myApp.getDatabasePath(name)!!

    override fun getExternalCacheDir(): File = myApp.externalCacheDir!!

    override fun getExternalFilesDir(type: String?): File = myApp.getExternalFilesDir(type)!!
}