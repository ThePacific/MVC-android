package com.pacific.mvc;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

public abstract class Fragment<T extends FragmentModel> extends RxFragment implements MVCController {

    protected Callback callback;
    protected T model;
    private boolean isNew = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isNew = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (model == null) {
            throw new RuntimeException("must instantiate Model in onCreate().");
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        model.onCreatedView(view);
        isNew = false;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (Callback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Fragment.Callback");
        }
    }

    public boolean isNew() {
        return isNew;
    }

    public interface Callback {

        void addFragment(@IdRes int container, Fragment Fragment, boolean isAddBack);

        void replaceFragment(@IdRes int container, Fragment Fragment, boolean isAddBack);

        void showFragment(Fragment Fragment);

        void hideFragment(Fragment Fragment);
    }
}
