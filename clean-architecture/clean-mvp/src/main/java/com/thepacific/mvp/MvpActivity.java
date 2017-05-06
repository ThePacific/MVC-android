package com.thepacific.mvp;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MvpActivity extends DaggerRxActivity implements MvpView {

    @Override
    public Observable<DisposeEvent> rxLifecycle() {
        return lifecycle();
    }

    @Override
    public Context context() {
        return getApplicationContext();
    }

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }
}

