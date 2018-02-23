package com.pacific.arch.data

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.annotation.WorkerThread
import android.text.TextUtils
import android.util.Log
import com.pacific.arch.rx.verifyWorkThread
import okhttp3.internal.Util
import okhttp3.internal.io.FileSystem
import okio.BufferedSink
import okio.Okio
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

@WorkerThread
fun readAsset(context: Context, name: String, path: String, overwrite: Boolean) {
    verifyWorkThread()
    var mPath = path
    mPath = fileSeparator(mPath)
    mkdirs(mPath)
    val assetManager = context.assets
    try {
        val file = File(mPath + name)
        if (FileSystem.SYSTEM.exists(file)) {
            if (overwrite) {
                FileSystem.SYSTEM.delete(file)
            } else {
                return
            }
        }
        file.createNewFile()
        val source = Okio.buffer(Okio.source(assetManager.open(name)))
        val sink = Okio.buffer(FileSystem.SYSTEM.sink(file))
        val bytes = ByteArray(1024)
        var nRead: Int = source.read(bytes)
        while (nRead != -1) {
            sink.write(bytes, 0, nRead)
            nRead = source.read(bytes)
        }
        Util.closeQuietly(source)
        Util.closeQuietly(sink)
    } catch (e: IOException) {
        Log.e("SystemIO", e.toString())
    }

}

@WorkerThread
fun toGallery(context: Context, bitmap: Bitmap, directory: File, image: String) {
    verifyWorkThread()
    var img = image
    val ext = ".jpg"
    if (!FileSystem.SYSTEM.exists(directory) || !directory.isDirectory) {
        directory.mkdir()
    }
    if (TextUtils.isEmpty(img)) {
        img = System.currentTimeMillis().toString() + ext
    }
    if (!img.endsWith(ext)) {
        img += ext
    }
    val file = File(directory, img)
    try {
        if (FileSystem.SYSTEM.exists(file)) {
            FileSystem.SYSTEM.delete(file)
        }
        file.createNewFile()
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        Util.closeQuietly(fos)
    } catch (e: FileNotFoundException) {
        Log.e("SystemIO", e.toString())
    } catch (e: IOException) {
        Log.e("SystemIO", e.toString())
    }

    try {
        MediaStore.Images.Media.insertImage(context.contentResolver, file.absolutePath, img, null)
        context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
    } catch (e: FileNotFoundException) {
        Log.e("SystemIO", e.toString())
    }

}

@WorkerThread
fun unzip(zipFile: File, directory: String) {
    verifyWorkThread()
    var mDirectory = directory
    try {
        val zipInputStream = ZipInputStream(FileInputStream(zipFile))
        val source = Okio.buffer(Okio.source(zipInputStream))
        mDirectory = fileSeparator(mDirectory)
        var sink: BufferedSink
        var zipEntry: ZipEntry? = zipInputStream.nextEntry
        while (zipEntry != null) {
            if (zipEntry.isDirectory) {
                mkdirs(mDirectory + File.separator + zipEntry.name)
            } else {
                sink = Okio.buffer(Okio.sink(File(mDirectory, zipEntry.name)))
                val bytes = ByteArray(1024)
                var nRead: Int = source.read(bytes)
                while (nRead != -1) {
                    sink.write(bytes, 0, nRead)
                    nRead = source.read(bytes)
                }
                Util.closeQuietly(sink)
                zipInputStream.closeEntry()
            }
            zipEntry = zipInputStream.nextEntry
        }
        Util.closeQuietly(source)
        Util.closeQuietly(zipInputStream)
    } catch (e: IOException) {
        Log.e("SystemIO", e.toString())
    }

}

@WorkerThread
fun mkdirs(directory: String): File {
    verifyWorkThread()
    val file = File(directory)
    if (!file.exists() || !file.isDirectory) {
        file.mkdirs()
    }
    return file
}

fun fileSeparator(directory: String): String {
    return if (directory.endsWith(File.separator)) {
        directory
    } else directory + File.separator
}

fun sdcard() = fileSeparator(Environment.getExternalStorageDirectory().absolutePath)

