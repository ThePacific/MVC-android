package com.thepacific.data;

import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.thepacific.data.cache.DiskCache;
import com.thepacific.data.cache.MemoryCache;
import com.thepacific.data.common.DataUtil;
import com.thepacific.data.http.Envelope;
import com.thepacific.data.http.IoError;
import com.thepacific.data.http.Source;
import com.thepacific.data.http.Status;
import com.thepacific.guava.Preconditions;
import com.google.gson.Gson;
import io.reactivex.Observable;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;

/**
 * A repository can get cached data {@link Repository#get(Object)}, or force
 * a call to network(skipping cache) {@link Repository#fetch(Object, boolean, boolean)}
 */
public abstract class Repository<T, R> {

  protected final Gson gson;
  protected final DiskCache diskCache;
  protected final MemoryCache memoryCache;
  protected String key;

  public Repository(Gson gson, DiskCache diskCache, MemoryCache memoryCache) {
    this.gson = gson;
    this.diskCache = diskCache;
    this.memoryCache = memoryCache;
  }

  /**
   * Return an Observable of {@link Source <R>} for request query
   * Data will be returned from oldest non expired source
   * Sources are memory cache, disk cache, finally network
   */
  @Nonnull
  @WorkerThread
  public final Observable<Source<R>> get(@Nonnull final T query) {
    DataUtil.requireWorkThread();
    return stream(query)
        .flatMap(it -> {
          if (it.status == Status.SUCCESS) {
            return Observable.just(it);
          }
          return load(query);
        })
        .flatMap(it -> {
          if (it.status == Status.SUCCESS) {
            return Observable.just(it);
          }
          return fetch(query, true, true);
        });
  }

  /***
   * @param query query parameters
   * @param toDisk true for persisting data to disk cache
   * @param toMemory true for persisting data to memory cache
   * @return an Observable of R for requested query skipping Memory & Disk Cache
   */
  @Nonnull
  @WorkerThread
  public final Observable<Source<R>> fetch(@Nonnull final T query, boolean toDisk,
      boolean toMemory) {
    DataUtil.requireWorkThread();
    Preconditions.checkNotNull(query);
    key = getKey(query);
    return dispatchNetwork(query)
        .flatMap(it -> {
          if (it.isSuccess()) {
            return onPersist(it, toDisk, toMemory, true, true);
          }
          return onError(it, false, false);
        });
  }

  /***
   * @param query query parameters
   * @return an Observable of R for requested from Disk Cache
   */
  @Nonnull
  @WorkerThread
  public final Observable<Source<R>> load(@Nonnull final T query) {
    DataUtil.requireWorkThread();
    Preconditions.checkNotNull(query);
    key = getKey(query);
    return Observable.defer(() -> {
      DiskCache.Entry diskEntry = diskCache.get(key);
      if (diskEntry == null) {
        return Observable.just(Source.irrelevant());
      }
      R newData = gson.fromJson(DataUtil.byteArray2String(diskEntry.data), dataType());
      if (diskEntry.isExpired() || isIrrelevant(newData)) {
        memoryCache.remove(key);
        clearDiskCache();
        return Observable.just(Source.irrelevant());
      }
      memoryCache.put(key, MemoryCache.Entry.create(newData, diskEntry.ttl));
      return Observable.just(Source.success(newData));
    });
  }

  /***
   * @param query query parameters
   * @return an Observable of R for requested from Memory Cache with refreshing query
   * It differs with {@link Repository#stream()}
   */
  @Nonnull
  public final Observable<Source<R>> stream(@Nonnull final T query) {
    Preconditions.checkNotNull(query);
    key = getKey(query);
    return stream(true);
  }

  /***
   * @return an Observable of R for requested from Memory Cache without refreshing query
   * It differs with {@link Repository#stream(Object)}
   */
  @Nonnull
  public final Observable<Source<R>> stream() {
    return stream(false);
  }

  /***
   * @param rejectExpired true to exclude expired data
   * @return an Observable of R for requested from Memory Cache without refreshing query
   * It differs with {@link Repository#stream(Object)}
   */
  @Nonnull
  public final Observable<Source<R>> stream(boolean rejectExpired) {
    return Observable.defer(() -> {
      MemoryCache.Entry memoryEntry = memoryCache.get(key, rejectExpired);
      //No need to check isExpired(), MemoryCache.get(key) has already done
      if (memoryEntry == null) {
        return Observable.just(Source.irrelevant());
      }
      R newData = (R) memoryEntry.data;
      if (isIrrelevant(newData)) {
        return Observable.just(Source.irrelevant());
      }
      return Observable.just(Source.success(newData));
    });
  }

  /***
   * @return an R from Memory Cache. when null, it will throw IllegalStateException
   */
  @Nonnull
  public final R memory() throws IllegalStateException {
    return memory(false);
  }

  /**
   * @param rejectExpired true to exclude expired data
   * @return an R from Memory Cache. When null, it will throw IllegalStateException
   */
  @Nonnull
  public final R memory(boolean rejectExpired) throws IllegalStateException {
    MemoryCache.Entry memoryEntry = memoryCache.get(key, rejectExpired);
    if (memoryEntry == null) {
      throw new IllegalStateException("Not supported");
    }
    R newData = (R) memoryEntry.data;
    if (isIrrelevant(newData)) {
      throw new IllegalStateException("Not supported");
    }
    return (R) memoryEntry.data;
  }

  public final void clearMemoryCache() {
    if (TextUtils.isEmpty(key)) {
      return;
    }
    memoryCache.remove(key);
  }

  @WorkerThread
  public final void clearDiskCache() {
    DataUtil.requireWorkThread();
    if (TextUtils.isEmpty(key)) {
      return;
    }
    try {
      diskCache.remove(key);
    } catch (IOException ignored) {
    }
  }


  /**
   * process network error
   *
   * @param envelope HTTP/HTTPS result
   * @param clearDiskCache true to clear disk cache
   * @param clearMemoryCache true to clear memory cache
   * @return an Observable of R
   */
  protected final Observable<Source<R>> onError(Envelope<R> envelope,
      boolean clearDiskCache,
      boolean clearMemoryCache)
      throws IOException {
    IoError ioError = new IoError(envelope.message(), envelope.code());
    if (DataUtil.isAccessFailure(ioError)) {
      diskCache.evictAll();
      memoryCache.evictAll();
    } else {
      if (clearDiskCache) {
        clearDiskCache();
      }
      if (clearMemoryCache) {
        clearMemoryCache();
      }
    }
    return Observable.just(Source.failure(ioError));
  }

  /**
   * persist data
   *
   * @param envelope HTTP/HTTPS result
   * @param toDisk true to persist data to disk cache
   * @param toMemory true for persisting data to memory cache
   * @param clearDiskCacheIfIrrelevant true to clear disk cache when result is irrelevant
   * @param clearMemoryCacheIfIrrelevant true to clear memory cache when result is irrelevant
   * @return an Observable of R
   */
  protected final Observable<Source<R>> onPersist(Envelope<R> envelope,
      boolean toDisk,
      boolean toMemory,
      boolean clearDiskCacheIfIrrelevant,
      boolean clearMemoryCacheIfIrrelevant) {
    R newData = envelope.data();
    if (isIrrelevant(newData)) {
      if (clearDiskCacheIfIrrelevant) {
        clearDiskCache();
      }
      if (clearMemoryCacheIfIrrelevant) {
        clearMemoryCache();
      }
      return Observable.just(Source.irrelevant());
    }
    long now = System.currentTimeMillis();
    long ttl = now + timeUnit().toMillis(ttl());
    long softTtl = now + timeUnit().toMillis(softTtl());
    Preconditions.checkState(ttl > now && softTtl > now && ttl >= softTtl);
    if (toDisk) {
      byte[] bytes = DataUtil.toJsonByteArray(newData, gson);
      diskCache.put(key, DiskCache.Entry.create(bytes, ttl, softTtl));
    } else {
      clearDiskCache();
    }
    if (toMemory) {
      memoryCache.put(key, MemoryCache.Entry.create(newData, ttl));
    } else {
      clearMemoryCache();
    }
    return Observable.just(Source.success(newData));
  }

  /**
   * @return default network cache time is 10 MINUTES
   */
  protected int ttl() {
    return 10;
  }

  /**
   * @return default refresh cache time
   */
  protected final int softTtl() {
    return ttl();
  }

  /**
   * Default TimeUnit is {@code TimeUnit.MINUTES}
   */
  protected TimeUnit timeUnit() {
    return TimeUnit.MINUTES;
  }

  /**
   * @return cache key
   */
  protected String getKey(T query) {
    return DataUtil.md5(String.valueOf(dataType()));
  }


  /**
   * @return to make sure never returning empty or null data
   */
  protected abstract boolean isIrrelevant(R data);

  /**
   * @return request HTTP/HTTPS API
   */
  protected abstract Observable<? extends Envelope<R>> dispatchNetwork(final T query);

  /**
   * @return gson deserialize Class type for R {@code Type typeOfT = R.class} for List<R> {@code
   * Type typeOfT = new TypeToken<List<R>>() { }.getType()}
   */
  protected abstract Type dataType();
}