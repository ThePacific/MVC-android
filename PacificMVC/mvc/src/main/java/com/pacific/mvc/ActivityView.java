package com.pacific.mvc;

import android.view.View;

public abstract class ActivityView<T extends Activity> extends MVCView {

    protected T activity;

    public ActivityView(T activity) {
        this.activity = activity;
    }

    @Override
    public T getController() {
        return activity;
    }

    @Override
    protected <V extends View> V retrieveView(int viewId) {
        return (V) activity.findViewById(viewId);
    }

    final void onCreate(Object... adapters){
        findView();
        setListener();
        setAdapter(adapters);
        initialize();
    }
}
