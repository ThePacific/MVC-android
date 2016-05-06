package com.pacific.mvc;

import android.support.annotation.CallSuper;

public abstract class ActivityModel<T extends ActivityView> extends MVCModel<T> {

    public ActivityModel(T mvcView) {
        super(mvcView);
    }

    @CallSuper
    public void onCreate() {
        mvcView.onCreate();
    }
}
