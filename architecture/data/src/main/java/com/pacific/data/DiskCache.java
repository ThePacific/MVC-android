package com.pacific.data;

import com.squareup.moshi.Json;
import com.squareup.moshi.Moshi;
import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Arrays;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;
import javax.inject.Singleton;
import okhttp3.internal.Util;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * An interface for a cache keyed by a String with a byte array as data It's extracted from {@link
 * okhttp3.Cache}
 */
@Singleton
@ThreadSafe
public final class DiskCache implements Closeable, Flushable {

  /**
   * Unlike {@link okhttp3.Cache} ENTRY_COUNT = 2 We don't save the CacheHeader and Respond in two
   * separated files Instead, we wrap them in {@link Entry}
   */
  private static final int ENTRY_COUNT = 1;

  private static final int VERSION = 201105;
  private static final int ENTRY_METADATA = 0;

  private final DiskLruCache cache;
  private final Moshi moshi;

  @Inject
  public DiskCache(Moshi moshi, File directory) {
    this(moshi, directory, 1024 * 1024 * 10);
  }

  public DiskCache(Moshi moshi, File directory, long maxSize) {
    cache = DiskLruCache.create(FileSystem.SYSTEM, directory, VERSION, ENTRY_COUNT, maxSize);
    this.moshi = moshi;
  }

  public Entry get(String key) {
    DiskLruCache.Snapshot snapshot;
    try {
      snapshot = cache.get(key);
      if (snapshot == null) {
        return null;
      }
    } catch (IOException e) {
      return null;
    }
    try {
      BufferedSource source = Okio.buffer(snapshot.getSource(0));
      String json = source.readUtf8();
      source.close();
      Util.closeQuietly(snapshot);
      return DataUtil.fromJson(json, moshi, Entry.class);
    } catch (IOException e) {
      Util.closeQuietly(snapshot);
      return null;
    }
  }

  public void put(String key, Entry entry) {
    DiskLruCache.Editor editor = null;
    try {
      editor = cache.edit(key);
      if (editor != null) {
        BufferedSink sink = Okio.buffer(editor.newSink(ENTRY_METADATA));
        //Entry.toString() is a json String
        sink.writeUtf8(entry.toString());
        sink.close();
        editor.commit();
      }
    } catch (IOException e) {
      abortQuietly(editor);
    }
  }

  public void remove(String key) throws IOException {
    cache.remove(key);
  }

  private void abortQuietly(DiskLruCache.Editor editor) {
    try {
      if (editor != null) {
        editor.abort();
      }
    } catch (IOException ignored) {
    }
  }

  public void initialize() throws IOException {
    cache.initialize();
  }

  public void delete() throws IOException {
    cache.delete();
  }

  public void evictAll() throws IOException {
    cache.evictAll();
  }

  public long size() throws IOException {
    return cache.size();
  }

  public long maxSize() {
    return cache.getMaxSize();
  }

  public File directory() {
    return cache.getDirectory();
  }

  public boolean isClosed() {
    return cache.isClosed();
  }

  @Override
  public void flush() throws IOException {
    cache.flush();
  }

  @Override
  public void close() throws IOException {
    cache.close();
  }

  /**
   * Data and metadata for an entry returned by the cache. It's extracted from android Volley
   * library. See {@code https://github.com/google/volley}
   */
  public static final class Entry {

    /**
     * The data returned from cache. Use {@link DataUtil#toJson(Object, Moshi, Type)}} to serialize
     * a data object
     */
    @Json(name = "data")
    public final byte[] data;

    /**
     * Time to live(TTL) for this record
     */
    @Json(name = "ttl")
    public final long ttl;

    /**
     * Soft TTL for this record
     */
    @Json(name = "softTtl")
    public final long softTtl;

    private Entry(byte[] data, long ttl, long softTtl) {
      this.data = data;
      this.ttl = ttl;
      this.softTtl = softTtl;
    }

    public static Entry create(byte[] data, long ttl, long softTtl) {
      return new Entry(data, ttl, softTtl);
    }

    /**
     * @return To a json String
     */
    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      builder.append("{")
          .append("data=")
          .append(Arrays.toString(data))
          .append(", ttl=")
          .append(ttl)
          .append(", softTtl=")
          .append(softTtl)
          .append("}");
      return builder.toString();
    }

    /**
     * True if the entry is expired.
     */
    public boolean isExpired() {
      return this.ttl < System.currentTimeMillis();
    }

    /**
     * True if a refresh is needed from the original data source.
     */
    public boolean refreshNeeded() {
      return this.softTtl < System.currentTimeMillis();
    }
  }
}
