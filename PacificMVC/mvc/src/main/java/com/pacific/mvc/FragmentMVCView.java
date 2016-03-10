package com.pacific.mvc;

import android.content.Context;
import android.view.View;

public abstract class FragmentMVCView<T extends Fragment> extends MVCView {

    protected T fragment;

    public FragmentMVCView(T fragment) {
        this.fragment = fragment;
    }

    @Override
    public T getController() {
        return fragment;
    }

    public Activity getActivity() {
        return (Activity) fragment.getActivity();
    }

    public Context getContext() {
        return fragment.getContext();
    }

    final void onCreatedView(View view) {
        this.view = view;
        findView();
        setListener();
        setAdapter();
        initialize();
    }
}
