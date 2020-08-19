package com.pacific.app.architecture.core.base

import com.pacific.app.architecture.data.base.AppContext
import com.pacific.app.architecture.core.CoreLib
import java.io.File

object AndroidContext : AppContext {

    override fun getCacheDir(): File = CoreLib.myApp.cacheDir

    override fun getFilesDir(): File = CoreLib.myApp.filesDir

    override fun getDatabasePath(name: String): File = CoreLib.myApp.getDatabasePath(name)!!

    override fun getExternalCacheDir(): File = CoreLib.myApp.externalCacheDir!!

    override fun getExternalFilesDir(type: String?): File = CoreLib.myApp.getExternalFilesDir(type)!!
}