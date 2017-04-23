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

    public static CompletableTransformer io(@Nonnull final Observable lifecycle,
                                            @Nonnull final DisposeEvent event) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable
                        .subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static CompletableTransformer computation(@Nonnull final Observable lifecycle,
                                                     @Nonnull final DisposeEvent event) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable
                        .subscribeOn(Schedulers.computation())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static CompletableTransformer trampoline(@Nonnull final Observable lifecycle,
                                                    @Nonnull final DisposeEvent event) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable
                        .subscribeOn(Schedulers.trampoline())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static CompletableTransformer newThread(@Nonnull final Observable lifecycle,
                                                   @Nonnull final DisposeEvent event) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable
                        .subscribeOn(Schedulers.newThread())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static CompletableTransformer single(@Nonnull final Observable lifecycle,
                                                @Nonnull final DisposeEvent event) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable
                        .subscribeOn(Schedulers.single())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static CompletableTransformer from(@Nonnull final Observable lifecycle,
                                              @Nonnull final DisposeEvent event,
                                              @Nonnull final Executor executor) {
        return new CompletableTransformer() {
            @Override
            public CompletableSource apply(Completable completable) {
                return completable
                        .subscribeOn(Schedulers.from(executor))
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private CompletableTransformerUtil() {
    }
}
