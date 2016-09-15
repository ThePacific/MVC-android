package com.pacific.mvc;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

public abstract class Activity<T extends ActivityModel> extends RxAppCompatActivity implements Fragment.Callback, MVCController {

    protected T model;

    @Override
    protected void onStart() {
        super.onStart();
        if (model == null) {
            throw new RuntimeException("must instantiate Model in onCreate().");
        }
    }

    @Override
    public void addFragment(@IdRes int container, Fragment Fragment, boolean isAddBack) {
        if (Fragment == null)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentByTag(Fragment.getClass().getSimpleName()) != null) {
            ft.show(fm.findFragmentByTag(Fragment.getClass().getSimpleName())).commit();
            return;
        }
        ft.add(container, Fragment, Fragment.getClass().getSimpleName());
        if (isAddBack) {
            ft.addToBackStack(Fragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    @Override
    public void replaceFragment(@IdRes int container, Fragment Fragment, boolean isAddBack) {
        if (Fragment == null)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentByTag(Fragment.getClass().getSimpleName()) != null) {
            ft.show(fm.findFragmentByTag(Fragment.getClass().getSimpleName())).commit();
            return;
        }
        ft.replace(container, Fragment, Fragment.getClass().getSimpleName());
        if (isAddBack) {
            ft.addToBackStack(Fragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    @Override
    public void showFragment(Fragment Fragment) {
        if (Fragment == null || !Fragment.isHidden())
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (Fragment.isAdded()) {
            ft.show(Fragment);
            ft.commit();
        }
    }

    @Override
    public void hideFragment(Fragment Fragment) {
        if (Fragment == null || Fragment.isHidden())
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (Fragment.isAdded()) {
            ft.hide(Fragment);
            ft.commit();
        }
    }
}
