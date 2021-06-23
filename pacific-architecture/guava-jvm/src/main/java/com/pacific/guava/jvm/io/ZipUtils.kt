package com.pacific.guava.jvm.io

import okhttp3.internal.closeQuietly
import okio.BufferedSink
import okio.buffer
import okio.sink
import okio.source
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * 创建目录
 */
fun mkdirs(directory: String): File {
    val file = File(directory)
    if (!file.exists() || !file.isDirectory) {
        file.mkdirs()
    }
    return file
}

/**
 * 追加文件分隔符
 */
fun ensureFileSeparator(directory: String): String {
    return if (directory.endsWith(File.separator)) {
        directory
    } else {
        directory + File.separator
    }
}

/**
 * 使用okio解压文件
 */
fun unzip(zipFile: File, directory: String) {
    var varDirectory = directory
    try {
        val zipInputStream = ZipInputStream(FileInputStream(zipFile))
        val source = zipInputStream.source().buffer()
        varDirectory = ensureFileSeparator(varDirectory)
        var sink: BufferedSink
        var zipEntry: ZipEntry? = zipInputStream.nextEntry
        while (zipEntry != null) {
            if (zipEntry.isDirectory) {
                mkdirs(varDirectory + File.separator + zipEntry.name)
            } else {
                sink = File(varDirectory, zipEntry.name).sink().buffer()
                val bytes = ByteArray(1024)
                var nRead: Int = source.read(bytes)
                while (nRead != -1) {
                    sink.write(bytes, 0, nRead)
                    nRead = source.read(bytes)
                }
                sink.closeQuietly()
                zipInputStream.closeEntry()
            }
            zipEntry = zipInputStream.nextEntry
        }
        source.closeQuietly()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}