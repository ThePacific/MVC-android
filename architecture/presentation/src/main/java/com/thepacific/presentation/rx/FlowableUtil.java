package com.thepacific.presentation.rx;

import com.thepacific.presentation.core.Event;
import com.uber.autodispose.FlowableScoper;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Executor;

public class FlowableUtil {

  private FlowableUtil() {
    throw new UnsupportedOperationException();
  }

  public static <T> FlowableTransformer<T, T> io() {
    return flowable -> flowable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> FlowableTransformer<T, T> computation() {
    return flowable -> flowable.subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> FlowableTransformer<T, T> trampoline() {
    return flowable -> flowable.subscribeOn(Schedulers.trampoline())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> FlowableTransformer<T, T> newThread() {
    return flowable -> flowable.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> FlowableTransformer<T, T> single() {
    return flowable -> flowable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> FlowableTransformer<T, T> from(final Executor executor) {
    return flowable -> flowable.subscribeOn(Schedulers.from(executor))
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> FlowableScoper<T> bindUntil(final Observable<Event> lifecycle,
      final Event e) {
    return new FlowableScoper<>(lifecycle.filter(it -> it == e).firstElement());
  }
}
