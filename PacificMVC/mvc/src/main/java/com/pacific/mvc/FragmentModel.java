package com.pacific.mvc;

import android.view.View;

public abstract class FragmentModel<T extends FragmentView> extends MVCModel<T> {
    public FragmentModel(T view) {
        super(view);
    }

    public void onCreate(View rootView) {
        view.onCreate(rootView, getArgs());
    }
}
