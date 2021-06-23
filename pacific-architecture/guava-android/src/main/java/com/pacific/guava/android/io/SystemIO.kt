package com.pacific.guava.android.io

import android.content.Context
import androidx.annotation.WorkerThread
import com.pacific.guava.android.ensureWorkThread
import com.pacific.guava.jvm.io.ensureFileSeparator
import com.pacific.guava.jvm.io.mkdirs
import okhttp3.internal.closeQuietly
import okhttp3.internal.io.FileSystem
import okio.buffer
import okio.source
import java.io.File
import java.io.IOException

/**
 * 复制文件
 *
 * @param targetDir 目标目录
 * @param file assert资源文件名字
 * @param overwrite 是否覆盖（如果需要）
 */
@WorkerThread
fun copy(targetDir: String, file: String, overwrite: Boolean): File? {
    return copy(targetDir, File(file), overwrite)
}

/**
 * 复制文件
 *
 * @param targetDir 目标目录
 * @param file assert资源文件名字
 * @param overwrite 是否覆盖（如果需要）
 */
@WorkerThread
fun copy(targetDir: String, file: File, overwrite: Boolean): File? {
    ensureWorkThread()
    try {
        val file = File(ensureFileSeparator(mkdirs(targetDir).absolutePath) + file.name)
        if (FileSystem.SYSTEM.exists(file)) {
            if (overwrite) {
                FileSystem.SYSTEM.delete(file)
            } else {
                return file
            }
        }
        file.createNewFile()
        val source = file.source().buffer()
        val sink = FileSystem.SYSTEM.sink(file).buffer()
        val bytes = ByteArray(1024)
        var nRead: Int = source.read(bytes)
        while (nRead != -1) {
            sink.write(bytes, 0, nRead)
            nRead = source.read(bytes)
        }
        source.closeQuietly()
        sink.closeQuietly()
        return file
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}

/**
 * 复制assert文件
 *
 * @param context 目标目录
 * @param name assert资源文件名字
 * @param targetDir 是否覆盖（如果需要）
 * @param overwrite 是否覆盖（如果需要）
 */
@WorkerThread
fun copyFromAsset(context: Context, name: String, targetDir: String, overwrite: Boolean) {
    ensureWorkThread()
    try {
        val file = File(ensureFileSeparator(mkdirs(targetDir).absolutePath) + name)
        if (FileSystem.SYSTEM.exists(file)) {
            if (overwrite) {
                FileSystem.SYSTEM.delete(file)
            } else {
                return
            }
        }
        file.createNewFile()
        val source = context.assets.open(name).source().buffer()
        val sink = FileSystem.SYSTEM.sink(file).buffer()
        val bytes = ByteArray(1024)
        var nRead: Int = source.read(bytes)
        while (nRead != -1) {
            sink.write(bytes, 0, nRead)
            nRead = source.read(bytes)
        }
        source.closeQuietly()
        sink.closeQuietly()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

/**
 * 读取assert文件内容
 *
 * @param context 上下文
 * @param name assert资源文件名字
 */
@WorkerThread
fun readFromAsset(context: Context, name: String): String {
    ensureWorkThread()
    val assetManager = context.assets
    var content = ""
    try {
        val source = assetManager.open(name).source().buffer()
        content = source.readUtf8()
        source.closeQuietly()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return content
}