package com.pacific.data.base

import java.io.File

interface AppContext {

    fun getCacheDir(): File

    fun getFilesDir(): File

    fun getDatabasePath(name: String): File

    fun getExternalCacheDir(): File

    fun getExternalFilesDir(type: String?): File
}