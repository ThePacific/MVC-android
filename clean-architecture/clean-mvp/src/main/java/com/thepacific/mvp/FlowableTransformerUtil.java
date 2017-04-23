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
    public static <T> FlowableTransformer<T, T> io(@Nonnull final Observable lifecycle,
                                                   @Nonnull final DisposeEvent event) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable
                        .subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> computation(@Nonnull final Observable lifecycle,
                                                            @Nonnull final DisposeEvent event) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable
                        .subscribeOn(Schedulers.computation())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> trampoline(@Nonnull final Observable lifecycle,
                                                           @Nonnull final DisposeEvent event) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable
                        .subscribeOn(Schedulers.trampoline())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> newThread(@Nonnull final Observable lifecycle,
                                                          @Nonnull final DisposeEvent event) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable
                        .subscribeOn(Schedulers.newThread())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> single(@Nonnull final Observable lifecycle,
                                                       @Nonnull final DisposeEvent event) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable
                        .subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> from(@Nonnull final Observable lifecycle,
                                                     @Nonnull final DisposeEvent event,
                                                     @Nonnull final Executor executor) {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> flowable) {
                return flowable
                        .subscribeOn(Schedulers.from(executor))
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private FlowableTransformerUtil() {
    }
}
