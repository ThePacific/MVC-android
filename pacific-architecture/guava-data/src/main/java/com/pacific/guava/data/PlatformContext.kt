package com.pacific.guava.data

import java.io.File

interface PlatformContext {

    fun getCacheDir(): File

    fun getFilesDir(): File

    fun getDatabasePath(name: String): File

    fun getExternalCacheDir(): File

    fun getExternalFilesDir(type: String?): File
}