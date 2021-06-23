package com.pacific.guava.data

import java.io.File
import java.io.InputStream

interface PlatformContext {

    fun getCacheDir(): File

    fun getFilesDir(): File

    fun getDatabasePath(name: String): File

    fun getExternalCacheDir(): File

    fun getExternalFilesDir(type: String?): File

    fun openAssert(fileName: String): InputStream

    fun openAssert(fileName: String, accessMode: Int): InputStream
}