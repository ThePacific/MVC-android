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

    public Context getContext() {
        return fragment.getActivity();
    }

    final void onCreatedView(View rootView, Object... adapters) {
        this.view = rootView;
        findView();
        setListener();
        setAdapter(adapters);
        initialize();
    }
}
