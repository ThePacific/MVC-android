package com.thepacific.presentation.rx;

import com.thepacific.presentation.core.Event;
import com.uber.autodispose.CompletableScoper;
import io.reactivex.CompletableTransformer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.Executor;

public class CompletableUtil {

  private CompletableUtil() {
    throw new UnsupportedOperationException();
  }

  public static CompletableTransformer io() {
    return completable -> completable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static CompletableTransformer computation() {
    return completable -> completable.subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static CompletableTransformer trampoline() {
    return completable -> completable.subscribeOn(Schedulers.trampoline())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static CompletableTransformer newThread() {
    return completable -> completable.subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static CompletableTransformer single() {
    return completable -> completable.subscribeOn(Schedulers.single())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static CompletableTransformer from(final Executor executor) {
    return completable -> completable.subscribeOn(Schedulers.from(executor))
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static CompletableScoper bindUntil(final Observable<Event> lifecycle, final Event e) {
    return new CompletableScoper(lifecycle.filter(it -> it == e).firstElement());
  }
}
