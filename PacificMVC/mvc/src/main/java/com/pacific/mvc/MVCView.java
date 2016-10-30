package com.pacific.mvc;

import android.view.View;

public abstract class MVCView {

    public abstract MVCController controller();

    protected abstract void initialize(Object... args);

    protected abstract <V extends View> V retrieveView(int viewId);
}