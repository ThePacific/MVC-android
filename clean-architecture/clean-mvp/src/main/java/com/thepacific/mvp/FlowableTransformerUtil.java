package com.thepacific.mvp;

import com.trello.rxlifecycle2.RxLifecycle;

import org.reactivestreams.Publisher;

import java.util.concurrent.Executor;

import javax.annotation.Nonnull;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FlowableTransformerUtil {
    public static <T, R> FlowableTransformer<T, T> io(@Nonnull final Observable<R> lifecycle,
                                                      @Nonnull final R event) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable.subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> FlowableTransformer<T, T> computation(@Nonnull final Observable<R> lifecycle,
                                                               @Nonnull final R event) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable.subscribeOn(Schedulers.computation())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> FlowableTransformer<T, T> trampoline(@Nonnull final Observable<R> lifecycle,
                                                              @Nonnull final R event) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable.subscribeOn(Schedulers.trampoline())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> FlowableTransformer<T, T> newThread(@Nonnull final Observable<R> lifecycle,
                                                             @Nonnull final R event) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable.subscribeOn(Schedulers.newThread())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> FlowableTransformer<T, T> single(@Nonnull final Observable<R> lifecycle,
                                                          @Nonnull final R event) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable.subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T, R> FlowableTransformer<T, T> from(@Nonnull final Observable<R> lifecycle,
                                                        @Nonnull final R event,
                                                        @Nonnull final Executor executor) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable.subscribeOn(Schedulers.from(executor))
                        .compose(RxLifecycle.<T, R>bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private FlowableTransformerUtil() {
    }
}
