package com.thepacific.data;

import com.thepacific.common.Preconditions;
import com.thepacific.data.cache.DiskCache;
import com.thepacific.data.cache.MemoryCache;
import com.thepacific.data.common.DataLayerUtil;
import com.thepacific.data.common.ExecutorUtil;
import com.thepacific.data.http.Envelope;
import com.thepacific.data.http.IoError;
import com.thepacific.data.http.OnAccessFailure;
import com.thepacific.data.http.Source;
import com.thepacific.data.http.Status;
import com.google.gson.Gson;
import io.reactivex.Observable;
import java.io.IOException;
import java.lang.reflect.Type;
import javax.annotation.Nonnull;

/**
 * A repository can get cached data {@link Repository#get(Object)}, or force
 * a call to network(skipping cache) {@link Repository#fetch(Object, boolean)}
 */
public abstract class Repository<T, R> {

  protected final Gson gson;
  protected final DiskCache diskCache;
  protected final MemoryCache memoryCache;
  protected final OnAccessFailure onAccessFailure;
  protected String key;

  public Repository(Gson gson,
      DiskCache diskCache,
      MemoryCache memoryCache,
      OnAccessFailure onAccessFailure) {
    this.gson = gson;
    this.diskCache = diskCache;
    this.memoryCache = memoryCache;
    this.onAccessFailure = onAccessFailure;
  }

  /**
   * Return an Observable of {@link Source <R>} for request query
   * Data will be returned from oldest non expired source
   * Sources are memory cache, disk cache, finally network
   */
  @Nonnull
  public final Observable<Source<R>> get(@Nonnull final T query) {
    ExecutorUtil.requireWorkThread();
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
          return fetch(query, true);
        });
  }

  /***
   * @param query query parameters
   * @param persist true for persisting data to disk
   * @return an Observable of R for requested query skipping Memory & Disk Cache
   */
  @Nonnull
  public final Observable<Source<R>> fetch(@Nonnull final T query, boolean persist) {
    ExecutorUtil.requireWorkThread();
    Preconditions.checkNotNull(query);
    key = getKey(query);
    return dispatchNetwork().flatMap(it -> {
      if (it.isSuccess()) {
        R newData = it.data();
        if (isIrrelevant(newData)) {
          return Observable.just(Source.irrelevant());
        }
        long ttl = DataLayerUtil.elapsedTimeMillis(ttl());
        long softTtl = DataLayerUtil.elapsedTimeMillis(softTtl());
        long now = System.currentTimeMillis();
        Preconditions.checkState(ttl > now && softTtl > now && ttl >= softTtl);
        if (persist) {
          byte[] bytes = DataLayerUtil.toJsonByteArray(newData, gson);
          diskCache.put(key, DiskCache.Entry.create(bytes, ttl, softTtl));
        } else {
          clearDiskCache();
        }
        memoryCache.put(key, MemoryCache.Entry.create(newData, ttl));
        return Observable.just(Source.success(newData));
      }

      IoError ioError = new IoError(it.message(), it.code());
      if (isAccessFailure(it.code())) {
        diskCache.evictAll();
        memoryCache.evictAll();
        ExecutorUtil.postToMainThread(() -> onAccessFailure.run(ioError));
        return Observable.empty();
      }
      memoryCache.remove(key);
      clearDiskCache();
      return Observable.just(Source.failure(ioError));
    });
  }

  /***
   * @param query query parameters
   * @return an Observable of R for requested from Disk Cache
   */
  @Nonnull
  public final Observable<Source<R>> load(@Nonnull final T query) {
    ExecutorUtil.requireWorkThread();
    Preconditions.checkNotNull(query);
    key = getKey(query);
    return Observable.defer(() -> {
      DiskCache.Entry diskEntry = diskCache.get(key);
      if (diskEntry == null) {
        return Observable.just(Source.irrelevant());
      }
      R newData = gson.fromJson(DataLayerUtil.byteArray2String(diskEntry.data), dataType());
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
    return stream();
  }

  /***
   * @return an Observable of R for requested from Memory Cache without refreshing query
   * It differs with {@link Repository#stream(Object)}
   */
  @Nonnull
  public final Observable<Source<R>> stream() {
    return Observable.defer(() -> {
      MemoryCache.Entry memoryEntry = memoryCache.get(key);
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
   * @return an R from Memory Cache
   */
  @Nonnull
  public final R memory() {
    MemoryCache.Entry memoryEntry = memoryCache.get(key);
    if (memoryEntry == null) {
      throw new IllegalStateException("Not supported");
    }
    R newData = (R) memoryEntry.data;
    if (isIrrelevant((R) memoryEntry.data)) {
      throw new IllegalStateException("Not supported");
    }
    return newData;
  }

  public final void clearMemoryCache() {
    memoryCache.remove(key);
  }

  public final void clearDiskCache() {
    ExecutorUtil.requireWorkThread();
    try {
      diskCache.remove(key);
    } catch (IOException ignored) {
    }
  }

  /**
   * @return default network cache time is 10. It must be {@code TimeUnit.MINUTES}
   */
  protected int ttl() {
    return 10;
  }

  /**
   * @return default refresh cache time is 5. It must be {@code TimeUnit.MINUTES}
   */
  protected final int softTtl() {
    return 5;
  }

  /**
   * @param code HTTP/HTTPS error code
   * @return some server does't support standard authorize rules
   */
  protected boolean isAccessFailure(final int code) {
    return code == 403 || code == 405;
  }

  /**
   * @return to make sure never returning empty or null data
   */
  protected abstract boolean isIrrelevant(R data);

  /**
   * @return request HTTP/HTTPS API
   */
  protected abstract Observable<Envelope<R>> dispatchNetwork();

  /**
   * @return cache key
   */
  protected abstract String getKey(T query);

  /**
   * @return gson deserialize Class type for R {@code Type typeOfT = R.class} for List<R> {@code
   * Type typeOfT = new TypeToken<List<R>>() { }.getType()}
   */
  protected abstract Type dataType();
}