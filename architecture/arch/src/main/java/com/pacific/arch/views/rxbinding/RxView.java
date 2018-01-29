package com.pacific.arch.views.rxbinding;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.pacific.arch.gauva.Preconditions2;

import io.reactivex.Observable;

public class RxView {
    private RxView() {
        throw new UnsupportedOperationException();
    }

    @CheckResult
    @NonNull
    public static Observable<View> clicks(@NonNull View view) {
        Preconditions2.checkNotNull(view, "view == null");
        return new ClickObservable(view);
    }

    @CheckResult
    @NonNull
    public static InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeEvents(@NonNull TextView view) {
        Preconditions2.checkNotNull(view, "view == null");
        return new TextViewAfterTextChangeEventObservable(view);
    }
}
