package com.thepacific.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DaggerRxActivity extends RxActivity implements HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;

    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    public AndroidInjector<Fragment> supportFragmentInjector() {
        return this.supportFragmentInjector;
    }
}
