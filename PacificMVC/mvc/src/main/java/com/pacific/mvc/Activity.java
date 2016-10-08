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
    public void addFragment(@IdRes int container, Fragment f, boolean isAddBack) {
        if (f == null)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentByTag(f.getClass().getSimpleName()) != null) {
            ft.show(fm.findFragmentByTag(f.getClass().getSimpleName())).commit();
            return;
        }
        ft.add(container, f, f.getClass().getSimpleName());
        if (isAddBack) {
            ft.addToBackStack(f.getClass().getSimpleName());
        }
        ft.commit();
    }

    @Override
    public void replaceFragment(@IdRes int container, Fragment f, boolean isAddBack) {
        if (f == null)
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fm.findFragmentByTag(f.getClass().getSimpleName()) != null) {
            ft.show(fm.findFragmentByTag(f.getClass().getSimpleName())).commit();
            return;
        }
        ft.replace(container, f, f.getClass().getSimpleName());
        if (isAddBack) {
            ft.addToBackStack(f.getClass().getSimpleName());
        }
        ft.commit();
    }

    @Override
    public void showFragment(Fragment f) {
        if (f == null || !f.isHidden())
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (f.isAdded()) {
            ft.show(f);
            ft.commit();
        }
    }

    @Override
    public void hideFragment(Fragment f) {
        if (f == null || f.isHidden())
            return;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (f.isAdded()) {
            ft.hide(f);
            ft.commit();
        }
    }
}
