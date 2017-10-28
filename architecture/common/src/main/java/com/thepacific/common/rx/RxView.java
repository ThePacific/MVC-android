package com.thepacific.common.rx;

import static com.thepacific.guava.Preconditions.checkNotNull;

import android.support.annotation.CheckResult;
import android.view.View;
import android.widget.TextView;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public class RxView {

  private RxView() {
    throw new UnsupportedOperationException();
  }

  @CheckResult
  @NonNull
  public static Observable<View> clicks(@NonNull View view) {
    checkNotNull(view, "view == null");
    return new ClickObservable(view);
  }

  @CheckResult
  @NonNull
  public static InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeEvents(
      @NonNull TextView view) {
    checkNotNull(view, "view == null");
    return new TextViewAfterTextChangeEventObservable(view);
  }

}
