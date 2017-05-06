package com.thepacific.mvp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public interface MvpView {

    Scheduler mainThread();

    Observable<DisposeEvent> rxLifecycle();

    Context context();
}
