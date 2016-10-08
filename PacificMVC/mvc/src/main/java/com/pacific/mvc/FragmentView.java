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
    public T controller() {
        return fragment;
    }

    @Override
    protected <V extends View> V retrieveView(int viewId) {
        return (V) view.findViewById(viewId);
    }

    public Context context() {
        return fragment.getActivity();
    }

    final void onCreate(View rootView, Object... args) {
        this.view = rootView;
        initialize(args);
    }
}
