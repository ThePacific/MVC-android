package com.thepacific.mvp;

import com.trello.rxlifecycle2.RxLifecycle;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SingleTransformerUtil {
    public static <T, R> SingleTransformer<T, T> io(@Nonnull final Observable<R> lifecycle,
                                                    @Nonnull final R event) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single.subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> SingleTransformer<T, T> computation(@Nonnull final Observable<R> lifecycle,
                                                             @Nonnull final R event) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single.subscribeOn(Schedulers.computation())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> SingleTransformer<T, T> trampoline(@Nonnull final Observable<R> lifecycle,
                                                            @Nonnull final R event) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single.subscribeOn(Schedulers.trampoline())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> SingleTransformer<T, T> newThread(@Nonnull final Observable<R> lifecycle,
                                                           @Nonnull final R event) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single.subscribeOn(Schedulers.newThread())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> SingleTransformer<T, T> single(@Nonnull final Observable<R> lifecycle,
                                                        @Nonnull final R event) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single.subscribeOn(Schedulers.single())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> SingleTransformer<T, T> from(@Nonnull final Observable<R> lifecycle,
                                                      @Nonnull final R event,
                                                      @Nonnull final Executor executor) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single.subscribeOn(Schedulers.from(executor))
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private SingleTransformerUtil() {
    }
}
