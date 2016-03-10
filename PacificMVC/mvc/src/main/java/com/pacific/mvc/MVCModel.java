package com.pacific.mvc;

public abstract class MVCModel<T extends MVCView> {
    protected T mvcView;

    public MVCModel(T mvcView) {
        this.mvcView = mvcView;
    }
}
