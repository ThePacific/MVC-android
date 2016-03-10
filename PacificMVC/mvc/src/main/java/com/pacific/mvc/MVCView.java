package com.pacific.mvc;

import android.view.View;

public abstract class MVCView implements View.OnClickListener {

    protected View view;

    protected <V extends View> V retrieveView(int viewId) {
        return (V) view.findViewById(viewId);
    }

    public abstract MVCController getController();

    protected abstract void findView();

    protected abstract void setListener();

    protected abstract void setAdapter();

    protected abstract void initialize();
}
