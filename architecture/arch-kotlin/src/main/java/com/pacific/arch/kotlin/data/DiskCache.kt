package com.pacific.arch.kotlin.data

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
import java.util.*
import javax.inject.Inject

/**
 * An interface for a cache keyed by a String with a ByteArray as data.
 * It's extracted from {@link okhttp3.Cache}.
 */
class DiskCache(private val moshi: Moshi, directory: File, maxSize: Long) : Closeable, Flushable {
    private val cache: DiskLruCache = DiskLruCache.create(FileSystem.SYSTEM,
            directory,
            VERSION,
            ENTRY_COUNT,
            maxSize)

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
            fromJson<Entry>(json, moshi, Entry::class.java)
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
        @JvmField
        val ENTRY_COUNT = 1

        @JvmField
        val VERSION = 201105

        @JvmField
        val ENTRY_METADATA = 0
    }

    /**
     * Data and metadata for an entry returned by the cache.
     * It's extracted from android Volley library.
     * See `https://github.com/google/volley`
     */
    class Entry private constructor(
            /**
             * The data returned from cache.
             * Use [DataUtil.toJson]}
             * to serialize a data object
             */
            @field:Json(name = "data")
            val data: ByteArray,
            /**
             * Time to live(TTL) for this record
             */
            @field:Json(name = "ttl")
            val TTL: Long,
            /**
             * Soft TTL for this record
             */
            @field:Json(name = "softTtl")
            val softTTL: Long) {

        /**
         * True if the entry is expired.
         */
        val isExpired: Boolean get() = this.TTL < System.currentTimeMillis()

        /**
         * @return To a json String
         */
        override fun toString(): String {
            val builder = StringBuilder()
            builder.append("{")
                    .append("data=")
                    .append(Arrays.toString(data))
                    .append(", ttl=")
                    .append(TTL)
                    .append(", softTtl=")
                    .append(softTTL)
                    .append("}")
            return builder.toString()
        }

        /**
         * True if a refresh is needed from the original data source.
         */
        fun refreshNeeded(): Boolean {
            return this.softTTL < System.currentTimeMillis()
        }

        companion object {
            @JvmStatic
            fun create(data: ByteArray, TTL: Long, softTTL: Long): Entry {
                return Entry(data, TTL, softTTL)
            }
        }
    }
}