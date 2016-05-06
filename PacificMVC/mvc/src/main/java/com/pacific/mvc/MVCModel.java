package com.pacific.mvc;

public abstract class MVCModel<T extends MVCView> {
    protected T view;

    public MVCModel(T view) {
        this.view = view;
    }
}
