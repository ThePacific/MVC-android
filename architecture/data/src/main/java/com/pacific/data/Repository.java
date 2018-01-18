package com.pacific.data;

import android.support.annotation.WorkerThread;
import android.text.TextUtils;

import com.pacific.guava.Preconditions;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import javax.annotation.Nonnull;

import io.reactivex.Observable;

import static com.pacific.data.SchedulersUtil.verifyWorkThread;

/**
 * A repository can get cached data {@link Repository#get(Object)}, or force a call to
 * network(skipping cache) {@link Repository#fetch(Object, boolean, boolean)}
 */
public abstract class Repository<T, R> {

    protected final Moshi moshi;
    protected final DiskCache diskCache;
    protected final MemoryCache memoryCache;
    protected String key;

    public Repository(Moshi moshi, DiskCache diskCache, MemoryCache memoryCache) {
        this.moshi = moshi;
        this.diskCache = diskCache;
        this.memoryCache = memoryCache;
    }

    /**
     * Return an Observable of {@link Source <R>} for request query Data will be returned from oldest
     * non expired source Source is from memory cache, disk cache and network
     */
    @Nonnull
    @WorkerThread
    @SuppressWarnings("unchecked")
    public final Observable<Source<R>> get(@Nonnull final T query) {
        verifyWorkThread();
        return stream(query, true)
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

    /**
     * @param query    query parameter
     * @param toDisk   true for persisting data to disk cache
     * @param toMemory true for persisting data to memory cache
     * @return an Observable of R from network
     */
    @Nonnull
    @WorkerThread
    @SuppressWarnings("unchecked")
    public final Observable<Source<R>> fetch(@Nonnull final T query, boolean toDisk, boolean toMemory) {
        verifyWorkThread();
        Preconditions.checkNotNull(query);
        key = getKey(query);
        return dispatchNetwork(query).flatMap(it -> {
            if (it.isSuccess()) {
                return onPersist(it, toDisk, toMemory, true, true);
            }
            return onError(it, false, false);
        });
    }

    /**
     * @param query query parameter
     * @return an Observable of R from Disk Cache
     */
    @Nonnull
    @WorkerThread
    @SuppressWarnings("unchecked")
    public final Observable<Source<R>> load(@Nonnull final T query) {
        verifyWorkThread();
        Preconditions.checkNotNull(query);
        key = getKey(query);
        return Observable.defer(() -> {
            DiskCache.Entry diskEntry = diskCache.get(key);
            if (diskEntry == null) {
                return Observable.just(Source.irrelevant());
            }
            String json = HttpUtil.byteArray2String(diskEntry.data);
            R newData = HttpUtil.fromJson(json, moshi, dataType());
            if (diskEntry.isExpired() || isIrrelevant(newData)) {
                memoryCache.remove(key);
                evictDiskCache();
                return Observable.just(Source.irrelevant());
            }
            memoryCache.put(key, MemoryCache.Entry.create(newData, diskEntry.ttl));
            return Observable.just(Source.success(newData));
        });
    }

    /**
     * @param query        query parameter
     * @param evictExpired true to evict expired data
     * @return an Observable of R from Memory Cache with refreshing key It differs with {@link
     * Repository#stream(boolean)}
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public final Observable<Source<R>> stream(@Nonnull final T query, boolean evictExpired) {
        Preconditions.checkNotNull(query);
        key = getKey(query);
        return stream(evictExpired);
    }

    /**
     * @param evictExpired true to evict expired data
     * @return an Observable of R from Memory Cache without refreshing key. It differs with {@link
     * Repository#stream(Object, boolean)}
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public final Observable<Source<R>> stream(boolean evictExpired) {
        return Observable.defer(() -> {
            MemoryCache.Entry entry = memoryCache.get(key, evictExpired);
            //No need to check isExpired(), MemoryCache.get() has already done
            if (entry == null) {
                return Observable.just(Source.irrelevant());
            }
            R newData = (R) entry.data;
            if (isIrrelevant(newData)) {
                return Observable.just(Source.irrelevant());
            }
            return Observable.just(Source.success(newData));
        });
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public final Observable<Source<R>> stream() {
        return stream(true);
    }

    /**
     * Be careful, it maybe throw IllegalStateException It's better to make sure you have data in
     * memory
     *
     * @param evictExpired true to evict expired data
     * @return an R from Memory Cache
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    public final R memory(boolean evictExpired) throws IllegalStateException {
        MemoryCache.Entry entry = memoryCache.get(key, evictExpired);
        if (entry == null) {
            throw new IllegalStateException("Not supported");
        }
        R newData = (R) entry.data;
        if (isIrrelevant(newData)) {
            throw new IllegalStateException("Not supported");
        }
        return (R) entry.data;
    }

    @Nonnull
    @SuppressWarnings("unchecked")
    public final R memory() throws IllegalStateException {
        return memory(false);
    }

    public final void evictMemoryCache() {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        memoryCache.remove(key);
    }

    @WorkerThread
    public final void evictDiskCache() {
        if (TextUtils.isEmpty(key)) {
            return;
        }
        verifyWorkThread();
        try {
            diskCache.remove(key);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * process network error
     *
     * @param envelope         HTTP/HTTPS result
     * @param evictDiskCache   true to evict disk cache
     * @param evictMemoryCache true to evict memory cache
     * @return an Observable of R
     */
    @SuppressWarnings("unchecked")
    protected final Observable<Source<R>> onError(Envelope<R> envelope, boolean evictDiskCache,
                                                  boolean evictMemoryCache) throws IOException {
        IOError ioError = new IOError(envelope.message(), envelope.code());
        if (isAccessFailure(ioError)) {
            diskCache.evictAll();
            memoryCache.evictAll();
        } else {
            if (evictDiskCache) {
                evictDiskCache();
            }
            if (evictMemoryCache) {
                evictMemoryCache();
            }
        }
        return Observable.just(Source.failure(ioError));
    }

    /**
     * persist data
     *
     * @param envelope                     HTTP/HTTPS result
     * @param toDisk                       true to persist data to disk cache
     * @param toMemory                     true for persisting data to memory cache
     * @param evictDiskCacheIfIrrelevant   true to evict disk cache if the result is irrelevant
     * @param evictMemoryCacheIfIrrelevant true to evict memory cache if the result is irrelevant
     * @return an Observable of R
     */
    @SuppressWarnings("unchecked")
    protected final Observable<Source<R>> onPersist(Envelope<R> envelope, boolean toDisk,
                                                    boolean toMemory, boolean evictDiskCacheIfIrrelevant, boolean evictMemoryCacheIfIrrelevant) {
        R newData = envelope.data();
        if (isIrrelevant(newData)) {
            if (evictDiskCacheIfIrrelevant) {
                evictDiskCache();
            }
            if (evictMemoryCacheIfIrrelevant) {
                evictMemoryCache();
            }
            return Observable.just(Source.irrelevant());
        }

        TimeUnit timeUnit = timeUnit();
        long now = System.currentTimeMillis();
        long ttl = now + timeUnit.toMillis(ttl());
        long softTtl = now + timeUnit.toMillis(softTtl());
        Preconditions.checkState(ttl > now && softTtl > now && ttl >= softTtl);
        if (toDisk) {
            byte[] bytes = HttpUtil.toJsonByteArray(newData, moshi, dataType());
            diskCache.put(key, DiskCache.Entry.create(bytes, ttl, softTtl));
        } else {
            evictDiskCache();
        }
        if (toMemory) {
            memoryCache.put(key, MemoryCache.Entry.create(newData, ttl));
        } else {
            evictMemoryCache();
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
        return HttpUtil.md5(String.valueOf(dataType()));
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
     * User {@code Types.newParameterizedType(...)}
     */
    protected abstract Type dataType();

    protected abstract boolean isAccessFailure(IOError ioError);
}