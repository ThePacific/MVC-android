package com.thepacific.mvp;

import android.support.v7.app.AppCompatActivity;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public interface MvpView {

    Scheduler mainThread();

    Observable<DisposeEvent> rxLifecycle();

    AppCompatActivity context();
}
