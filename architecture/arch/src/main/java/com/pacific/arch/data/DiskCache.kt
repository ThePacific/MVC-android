package com.pacific.arch.data

import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import okhttp3.internal.Util
import okhttp3.internal.cache.DiskLruCache
import okhttp3.internal.io.FileSystem
import okio.Okio
import java.io.Closeable
import java.io.File
import java.io.Flushable
import java.io.IOException
import javax.inject.Inject

class DiskCache(private val moshi: Moshi, directory: File, maxSize: Long) : Closeable, Flushable {
    private val cache = DiskLruCache.create(FileSystem.SYSTEM, directory, VERSION, ENTRY_COUNT, maxSize)

    @Inject constructor(moshi: Moshi, directory: File) : this(moshi, directory, 1024 * 1024 * 100)

    fun get(key: String): Entry? {
        val snapshot: DiskLruCache.Snapshot?
        try {
            snapshot = cache.get(key)
            if (snapshot == null) {
                return null
            }
        } catch (e: IOException) {
            return null
        }

        return try {
            val source = Okio.buffer(snapshot.getSource(0))
            val json = source.readUtf8()
            source.close()
            Util.closeQuietly(snapshot)
            fromJson<Entry>(json, Entry::class.java, moshi)
        } catch (e: IOException) {
            Util.closeQuietly(snapshot)
            null
        }

    }

    fun put(key: String, entry: Entry) {
        var editor: DiskLruCache.Editor? = null
        try {
            editor = cache.edit(key)
            if (editor != null) {
                val sink = Okio.buffer(editor.newSink(ENTRY_METADATA))
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

    private fun abortQuietly(editor: DiskLruCache.Editor?) {
        try {
            editor?.abort()
        } catch (ignored: IOException) {
        }

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

    fun maxSize(): Long {
        return cache.maxSize
    }

    fun directory(): File {
        return cache.directory
    }

    fun isClosed(): Boolean {
        return cache.isClosed
    }

    @Throws(IOException::class)
    override fun flush() {
        cache.flush()
    }

    @Throws(IOException::class)
    override fun close() {
        cache.close()
    }

    companion object {
        /**
         * Unlike [okhttp3.Cache] ENTRY_COUNT = 2.
         * We don't save the CacheHeader and Respond in two separated files.
         * Instead, we wrap them in [DiskCache.Entry].
         */
        const val ENTRY_COUNT = 1

        const val VERSION = 201105

        const val ENTRY_METADATA = 0
    }

    class Entry(
            @JvmField @Json(name = "data") val data: ByteArray,
            @JvmField @Json(name = "TTL") val TTL: Long,
            @JvmField @Json(name = "softTTL") val softTTL: Long) {

        fun isExpired() = this.TTL < System.currentTimeMillis()

        fun refreshNeeded() = this.softTTL < System.currentTimeMillis()

        override fun toString() = toJson(this, DiskCache.Entry::class.java)
    }
}