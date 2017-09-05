package com.thepacific.data.cache;

import android.support.v4.util.LruCache;
import com.google.gson.annotations.SerializedName;
import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.CheckForNull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@ThreadSafe
public final class MemoryCache {

  private final LruCache<String, Entry> cache;
  private final List<String> keys = new ArrayList<>();

  @Inject
  public MemoryCache() {
    this(Integer.MAX_VALUE);
  }

  public MemoryCache(int maxSize) {
    this.cache = new LruCache<>(maxSize);
  }

  private void clearExpired() {
    Completable.fromAction(
        () -> {
          String key;
          for (int i = 0; i < keys.size(); i++) {
            key = keys.get(i);
            Entry value = cache.get(key);
            if (value != null && value.isExpired()) {
              remove(key);
            }
          }
        })
        .subscribeOn(Schedulers.single())
        .subscribe();
  }

  @CheckForNull
  public synchronized Entry get(String key, boolean excludeExpired) {
    Entry value = cache.get(key);
    if (excludeExpired) {
      if (value != null && value.isExpired()) {
        remove(key);
        clearExpired();
        return null;
      }
      clearExpired();
    }
    return value;
  }

  public synchronized Entry put(String key, Entry value) {
    if (!keys.contains(key)) {
      keys.add(key);
    }
    Entry oldValue = cache.put(key, value);
    clearExpired();
    return oldValue;
  }

  public Entry remove(String key) {
    keys.remove(key);
    return cache.remove(key);
  }

  public Map<String, Entry> snapshot() {
    return cache.snapshot();
  }

  public void trimToSize(int maxSize) {
    cache.trimToSize(maxSize);
  }

  public int createCount() {
    return cache.createCount();
  }

  public void evictAll() {
    cache.evictAll();
  }

  public int evictionCount() {
    return cache.evictionCount();
  }

  public int hitCount() {
    return cache.hitCount();
  }

  public int maxSize() {
    return cache.maxSize();
  }

  public int missCount() {
    return cache.missCount();
  }

  public int putCount() {
    return cache.putCount();
  }

  public int size() {
    return cache.size();
  }

  @Immutable
  public static final class Entry {

    @SerializedName("data")
    public final Object data;

    @SerializedName("ttl")
    public final long ttl;

    private Entry(Object data, long ttl) {
      this.data = data;
      this.ttl = ttl;
    }

    public boolean isExpired() {
      return this.ttl < System.currentTimeMillis();
    }

    public static Entry create(Object data, long ttl) {
      return new Entry(data, ttl);
    }
  }
}