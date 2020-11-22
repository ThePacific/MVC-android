package com.pacific.app.architecture.core.ext.rxjava3;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;

import dagger.internal.Preconditions;
import io.reactivex.rxjava3.core.Observable;

public class RxView {

    private RxView() {
        throw new UnsupportedOperationException();
    }

    @CheckResult
    @NonNull
    public static Observable<View> clicks(@NonNull View view) {
        Preconditions.checkNotNull(view);
        return new ClickObservable(view);
    }

    @CheckResult
    @NonNull
    public static InitialValueObservable<TextViewAfterTextChangeEvent> afterTextChangeEvents(
            @NonNull TextView view
    ) {
        Preconditions.checkNotNull(view);
        return new TextViewAfterTextChangeEventObservable(view);
    }
}
