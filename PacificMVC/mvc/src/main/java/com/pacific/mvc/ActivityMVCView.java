package com.pacific.mvc;

public abstract class ActivityMVCView<T extends Activity> extends MVCView {

    protected T activity;

    public ActivityMVCView(T activity) {
        this.activity = activity;
        this.view = activity.getWindow().getDecorView();
    }

    @Override
    public T getController() {
        return activity;
    }
}
