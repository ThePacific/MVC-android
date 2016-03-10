package com.pacific.mvc;

import android.view.View;

public abstract class FragmentMVCModel<T extends FragmentMVCView> extends MVCModel<T> {
    public FragmentMVCModel(T mvcView) {
        super(mvcView);
    }

    final void onCreatedView(View view) {
        mvcView.onCreatedView(view);
    }
}
