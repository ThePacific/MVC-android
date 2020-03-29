package com.square.data.repository.cache

import com.square.data.http.okhttp3.gentle
import com.squareup.moshi.Moshi
import okhttp3.internal.cache.DiskLruCache
import okhttp3.internal.closeQuietly
import okhttp3.internal.io.FileSystem
import okio.buffer
import java.io.Closeable
import java.io.File
import java.io.Flushable
import java.io.IOException

class DiskCache(
    private val moshi: Moshi,
    directory: File,
    maxSize: Long
) : Closeable, Flushable {

    private val cache = DiskLruCache.create(
        FileSystem.SYSTEM, directory, VERSION, ENTRY_COUNT, maxSize
    )

    constructor(moshi: Moshi, directory: File) : this(moshi, directory, 1024 * 1024 * 64)

    fun get(key: String): DiskCacheEntry? {
        val snapshot: DiskLruCache.Snapshot?
        try {
            snapshot = cache[key]
            if (snapshot == null) {
                return null
            }
        } catch (e: IOException) {
            return null
        }

        return try {
            val source = snapshot.getSource(0).buffer()
            val json = source.readUtf8()
            source.close()
            snapshot.closeQuietly()
            moshi.adapter(DiskCacheEntry::class.java).gentle().fromJson(json)
        } catch (e: IOException) {
            snapshot.closeQuietly()
            null
        }
    }

    fun put(key: String, entry: DiskCacheEntry) {
        var editor: DiskLruCache.Editor? = null
        try {
            editor = cache.edit(key)
            if (editor != null) {
                val sink = editor.newSink(ENTRY_METADATA).buffer()
                sink.writeUtf8(entry.toString())
                sink.close()
                editor.commit()
            }
        } catch (e: IOException) {
            abortQuietly(editor)
        }
    }

    @Throws(IOException::class)
    fun remove(key: String) {
        cache.remove(key)
    }

    @Throws(IOException::class)
    fun initialize() {
        cache.initialize()
    }

    @Throws(IOException::class)
    fun delete() {
        cache.delete()
    }

    @Throws(IOException::class)
    fun evictAll() {
        cache.evictAll()
    }

    @Throws(IOException::class)
    fun size(): Long {
        return cache.size()
    }

    @Throws(IOException::class)
    override fun flush() {
        cache.flush()
    }

    @Throws(IOException::class)
    override fun close() {
        cache.close()
    }

    private fun abortQuietly(editor: DiskLruCache.Editor?) {
        try {
            editor?.abort()
        } catch (ignored: IOException) {
            ignored.printStackTrace()
        }
    }

    fun maxSize() = cache.maxSize

    fun directory() = cache.directory

    fun isClosed() = cache.isClosed()

    companion object {

        const val ENTRY_COUNT = 1

        const val VERSION = 201105

        const val ENTRY_METADATA = 0
    }
}