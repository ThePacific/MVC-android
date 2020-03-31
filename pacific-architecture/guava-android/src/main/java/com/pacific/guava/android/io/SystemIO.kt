package com.pacific.guava.android.io

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.provider.MediaStore
import android.text.TextUtils
import androidx.annotation.WorkerThread
import com.pacific.guava.android.ensureWorkThread
import com.pacific.guava.io.ensureFileSeparator
import com.pacific.guava.io.mkdirs
import okhttp3.internal.closeQuietly
import okhttp3.internal.io.FileSystem
import okio.buffer
import okio.source
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

@WorkerThread
fun copyFromAsset(context: Context, name: String, path: String, overwrite: Boolean) {
    ensureWorkThread()
    try {
        val file = File(ensureFileSeparator(mkdirs(path).absolutePath) + name)
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

@WorkerThread
fun readFromAsset(context: Context, name: String, path: String): String {
    ensureWorkThread()
    var localPath = path
    localPath = ensureFileSeparator(localPath)
    mkdirs(localPath)
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

@WorkerThread
fun saveBitmapToGallery(context: Context, bitmap: Bitmap, directory: File, imageName: String) {
    ensureWorkThread()
    var saveImageName = imageName
    if (!FileSystem.SYSTEM.exists(directory) || !directory.isDirectory) {
        directory.mkdir()
    }
    if (TextUtils.isEmpty(saveImageName)) {
        saveImageName = System.currentTimeMillis().toString() + ".jpg"
    }
    if (
        !saveImageName.endsWith(".jpg", true) &&
        !saveImageName.endsWith(".png", true) &&
        !saveImageName.endsWith(".jpeg", true) &&
        !saveImageName.endsWith(".webp", true)
    ) {
        saveImageName += ".jpg"
    }
    val file = File(directory, saveImageName)
    try {
        if (FileSystem.SYSTEM.exists(file)) {
            FileSystem.SYSTEM.delete(file)
        }
        file.createNewFile()
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        fos.closeQuietly()

        context.contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, saveImageName)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, file.absolutePath)
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        )
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}