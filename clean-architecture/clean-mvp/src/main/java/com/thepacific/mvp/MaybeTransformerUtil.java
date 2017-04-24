package com.thepacific.mvp;

import com.trello.rxlifecycle2.RxLifecycle;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.MaybeTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MaybeTransformerUtil {

    public static <T, R> MaybeTransformer<T, T> io(@Nonnull final Observable<R> lifecycle,
                                                   @Nonnull final R event) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe.subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> MaybeTransformer<T, T> computation(@Nonnull final Observable<R> lifecycle,
                                                            @Nonnull final R event) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe.subscribeOn(Schedulers.computation())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> MaybeTransformer<T, T> trampoline(@Nonnull final Observable<R> lifecycle,
                                                           @Nonnull final R event) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe.subscribeOn(Schedulers.trampoline())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> MaybeTransformer<T, T> newThread(@Nonnull final Observable<R> lifecycle,
                                                          @Nonnull final R event) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe.subscribeOn(Schedulers.newThread())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> MaybeTransformer<T, T> single(@Nonnull final Observable<R> lifecycle,
                                                       @Nonnull final R event) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe.subscribeOn(Schedulers.single())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> MaybeTransformer<T, T> from(@Nonnull final Observable<R> lifecycle,
                                                     @Nonnull final R event,
                                                     @Nonnull final Executor executor) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe.subscribeOn(Schedulers.from(executor))
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private MaybeTransformerUtil() {
    }
}
