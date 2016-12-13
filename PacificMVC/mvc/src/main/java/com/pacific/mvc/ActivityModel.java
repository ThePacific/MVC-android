package com.pacific.mvc;

public abstract class ActivityModel<T extends ActivityView> extends MVCModel<T> {

    public ActivityModel(T view) {
        super(view);
    }

    public void onCreate(Object... args) {
        view.onCreate(args);
    }
}
