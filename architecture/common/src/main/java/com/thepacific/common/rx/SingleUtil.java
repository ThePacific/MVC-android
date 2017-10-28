package com.thepacific.common.rx;

import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Executor;

public class SingleUtil {

  private SingleUtil() {
    throw new UnsupportedOperationException();
  }

  public static <T> SingleTransformer<T, T> io() {
    return single -> single.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> SingleTransformer<T, T> computation() {
    return single -> single.subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> SingleTransformer<T, T> trampoline() {
    return single -> single.subscribeOn(Schedulers.trampoline())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> SingleTransformer<T, T> newThread() {
    return single -> single.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> SingleTransformer<T, T> single() {
    return single -> single.subscribeOn(Schedulers.single())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> SingleTransformer<T, T> from(final Executor executor) {
    return single -> single.subscribeOn(Schedulers.from(executor))
        .observeOn(AndroidSchedulers.mainThread());
  }
}

