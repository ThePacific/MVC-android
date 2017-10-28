package com.pacific.common.rx;

import io.reactivex.MaybeTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Executor;

public class MaybeUtil {

  private MaybeUtil() {
    throw new UnsupportedOperationException();
  }

  public static <T> MaybeTransformer<T, T> io() {
    return maybe -> maybe.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> MaybeTransformer<T, T> computation() {
    return maybe -> maybe.subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> MaybeTransformer<T, T> trampoline() {
    return maybe -> maybe.subscribeOn(Schedulers.trampoline())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> MaybeTransformer<T, T> newThread() {
    return maybe -> maybe.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> MaybeTransformer<T, T> single() {
    return maybe -> maybe.subscribeOn(Schedulers.single())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> MaybeTransformer<T, T> from(final Executor executor) {
    return maybe -> maybe.subscribeOn(Schedulers.from(executor))
        .observeOn(AndroidSchedulers.mainThread());
  }
}
