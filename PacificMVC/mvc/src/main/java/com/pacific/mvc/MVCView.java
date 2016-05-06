package com.pacific.mvc;

import android.view.View;

public abstract class MVCView implements View.OnClickListener {

    public abstract MVCController getController();

    protected abstract void findView();

    protected abstract void setListener();

    protected abstract void setAdapter();

    protected abstract void initialize();
    
    protected abstract <V extends View> V retrieveView(int viewId);
}
