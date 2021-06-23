package com.pacific.guava.android.text

import android.app.Application
import android.net.Uri
import androidx.annotation.WorkerThread
import com.pacific.guava.android.ensureWorkThread
import okio.Buffer
import okio.ByteString.Companion.encodeUtf8
import okio.source

/**
 * uri地址进行md5编码
 */
fun Uri.md5Key(): String = this.toString().encodeUtf8().md5().hex()

/**
 * uri转bytes
 *
 * @param app Application上下文
 */
@WorkerThread
fun Uri.toBytes(app: Application): ByteArray {
    ensureWorkThread()// 强制工作线程
    val buffer = Buffer()// 文件内容写入okio
    app.contentResolver.openInputStream(this)?.use {
        buffer.writeAll(it.source())
    } ?: throw Exception(
        "Could not open $this"
    )
    return buffer.readByteArray()
}