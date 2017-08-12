package com.thepacific.presentation.rx;

import com.thepacific.presentation.core.Event;
import com.uber.autodispose.ObservableScoper;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Executor;

public class ObservableUtil {

  private ObservableUtil() {
    throw new UnsupportedOperationException();
  }

  public static <T> ObservableTransformer<T, T> io() {
    return observable -> observable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> ObservableTransformer<T, T> computation() {
    return observable -> observable.subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> ObservableTransformer<T, T> trampoline() {
    return observable -> observable.subscribeOn(Schedulers.trampoline())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> ObservableTransformer<T, T> newThread() {
    return observable -> observable.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> ObservableTransformer<T, T> single() {
    return observable -> observable.subscribeOn(Schedulers.single())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> ObservableTransformer<T, T> from(final Executor executor) {
    return observable -> observable.subscribeOn(Schedulers.from(executor))
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> ObservableScoper<T> bindUntil(final Observable<Event> lifecycle,
      final Event e) {
    return new ObservableScoper<>(lifecycle.filter(it -> it == e).firstElement());
  }
}
