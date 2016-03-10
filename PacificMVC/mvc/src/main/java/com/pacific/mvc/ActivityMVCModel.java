package com.pacific.mvc;

public abstract class ActivityMVCModel<T extends ActivityMVCView> extends MVCModel<T> {

    public ActivityMVCModel(T mvcView) {
        super(mvcView);
    }

    public void onCreate() {
        mvcView.findView();
        mvcView.setListener();
        mvcView.setAdapter();
        mvcView.initialize();
    }
}
