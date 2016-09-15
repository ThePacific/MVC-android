package com.pacific.mvc;

public abstract class ActivityModel<T extends ActivityView> extends MVCModel<T> {

    public ActivityModel(T view) {
        super(view);
    }

    public void onCreate() {
        view.onCreate(getAdapters());
    }
}
