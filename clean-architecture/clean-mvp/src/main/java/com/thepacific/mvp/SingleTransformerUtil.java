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
    public static <T> SingleTransformer<T, T> io(@Nonnull final Observable lifecycle,
                                                 @Nonnull final DisposeEvent event) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single
                        .subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> SingleTransformer<T, T> computation(@Nonnull final Observable lifecycle,
                                                          @Nonnull final DisposeEvent event) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single
                        .subscribeOn(Schedulers.computation())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> SingleTransformer<T, T> trampoline(@Nonnull final Observable lifecycle,
                                                         @Nonnull final DisposeEvent event) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single
                        .subscribeOn(Schedulers.trampoline())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> SingleTransformer<T, T> newThread(@Nonnull final Observable lifecycle,
                                                        @Nonnull final DisposeEvent event) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single
                        .subscribeOn(Schedulers.newThread())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> SingleTransformer<T, T> single(@Nonnull final Observable lifecycle,
                                                     @Nonnull final DisposeEvent event) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single
                        .subscribeOn(Schedulers.single())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> SingleTransformer<T, T> from(@Nonnull final Observable lifecycle,
                                                   @Nonnull final DisposeEvent event,
                                                   @Nonnull final Executor executor) {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> single) {
                return single
                        .subscribeOn(Schedulers.from(executor))
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private SingleTransformerUtil() {
    }
}
