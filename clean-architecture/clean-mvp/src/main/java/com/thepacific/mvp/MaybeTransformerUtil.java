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

    public static <T> MaybeTransformer<T, T> io(@Nonnull final Observable lifecycle,
                                                @Nonnull final DisposeEvent event) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe
                        .subscribeOn(Schedulers.io())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> MaybeTransformer<T, T> computation(@Nonnull final Observable lifecycle,
                                                         @Nonnull final DisposeEvent event) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe
                        .subscribeOn(Schedulers.computation())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> MaybeTransformer<T, T> trampoline(@Nonnull final Observable lifecycle,
                                                        @Nonnull final DisposeEvent event) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe
                        .subscribeOn(Schedulers.trampoline())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> MaybeTransformer<T, T> newThread(@Nonnull final Observable lifecycle,
                                                       @Nonnull final DisposeEvent event) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe
                        .subscribeOn(Schedulers.newThread())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> MaybeTransformer<T, T> single(@Nonnull final Observable lifecycle,
                                                    @Nonnull final DisposeEvent event) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe
                        .subscribeOn(Schedulers.single())
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> MaybeTransformer<T, T> from(@Nonnull final Observable lifecycle,
                                                  @Nonnull final DisposeEvent event,
                                                  @Nonnull final Executor executor) {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(Maybe<T> maybe) {
                return maybe
                        .subscribeOn(Schedulers.from(executor))
                        .compose(RxLifecycle.bindUntilEvent(lifecycle, event))
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private MaybeTransformerUtil() {
    }
}
