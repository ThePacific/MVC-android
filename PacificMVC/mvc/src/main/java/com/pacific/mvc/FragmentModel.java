package com.pacific.mvc;

import android.support.annotation.CallSuper;
import android.view.View;

public abstract class FragmentModel<T extends FragmentView> extends MVCModel<T> {
    public FragmentModel(T mvcView) {
        super(mvcView);
    }

    @CallSuper
    public void onCreatedView(View rootView) {
        mvcView.onCreatedView(rootView);
    }
}
