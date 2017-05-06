package com.thepacific.mvp;

import android.content.Context;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class MvpFragment extends DaggerRxFragment implements MvpView {
    @Override
    public Observable<DisposeEvent> rxLifecycle() {
        return lifecycle();
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public Scheduler mainThread() {
        return AndroidSchedulers.mainThread();
    }
}
