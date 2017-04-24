package com.thepacific.mvp;

import com.trello.rxlifecycle2.RxLifecycle;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ObservableTransformerUtil {
    public static <T, R> ObservableTransformer<T, T> io(@Nonnull final Observable<R> lifecycle,
                                                        @Nonnull final R event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> ObservableTransformer<T, T> computation(@Nonnull final Observable<R> lifecycle,
                                                                 @Nonnull final R event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.computation())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> ObservableTransformer<T, T> trampoline(@Nonnull final Observable<R> lifecycle,
                                                                @Nonnull final R event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.trampoline())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> ObservableTransformer<T, T> newThread(@Nonnull final Observable<R> lifecycle,
                                                               @Nonnull final R event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.newThread())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> ObservableTransformer<T, T> single(@Nonnull final Observable<R> lifecycle,
                                                            @Nonnull final R event) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.single())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> ObservableTransformer<T, T> from(@Nonnull final Observable<R> lifecycle,
                                                          @Nonnull final R event,
                                                          @Nonnull final Executor executor) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.from(executor))
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private ObservableTransformerUtil() {
    }
}
