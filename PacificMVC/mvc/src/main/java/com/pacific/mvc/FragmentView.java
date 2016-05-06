package com.pacific.mvc;

import android.content.Context;
import android.view.View;

public abstract class FragmentView<T extends Fragment> extends MVCView {

    protected View view;
    protected T fragment;

    public FragmentView(T fragment) {
        this.fragment = fragment;
    }

    @Override
    public T getController() {
        return fragment;
    }

    @Override
    protected <V extends View> V retrieveView(int viewId) {
        return (V) view.findViewById(viewId);
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
