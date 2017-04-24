package com.thepacific.mvp;

import com.trello.rxlifecycle2.RxLifecycle;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Observable;
import io.reactivex.CompletableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CompletableTransformerUtil {
    public static <R> CompletableTransformer io(@Nonnull final Observable<R> lifecycle,
                                                @Nonnull final R event) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable.subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <R> CompletableTransformer computation(@Nonnull final Observable<R> lifecycle,
                                                         @Nonnull final R event) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable.subscribeOn(Schedulers.computation())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <R> CompletableTransformer trampoline(@Nonnull final Observable<R> lifecycle,
                                                        @Nonnull final R event) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable.subscribeOn(Schedulers.trampoline())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <R> CompletableTransformer newThread(@Nonnull final Observable<R> lifecycle,
                                                       @Nonnull final R event) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable.subscribeOn(Schedulers.newThread())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <R> CompletableTransformer single(@Nonnull final Observable<R> lifecycle,
                                                    @Nonnull final R event) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable.subscribeOn(Schedulers.single())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <R> CompletableTransformer from(@Nonnull final Observable<R> lifecycle,
                                                  @Nonnull final R event,
                                                  @Nonnull final Executor executor) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable.subscribeOn(Schedulers.from(executor))
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private CompletableTransformerUtil() {
    }
}
