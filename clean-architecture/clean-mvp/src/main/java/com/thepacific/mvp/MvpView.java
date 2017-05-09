package com.thepacific.mvp;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public interface MvpView {
    Scheduler mainThread();

    Observable<DisposeEvent> rxLifecycle();

    Context context();
}
