package com.thepacific.mvp;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.thepacific.dagger.AndroidInjection;
import com.thepacific.dagger.AndroidInjector;
import com.thepacific.dagger.DispatchingAndroidInjector;
import com.thepacific.dagger.HasFragmentInjector;

import javax.inject.Inject;

public class DaggerRxActivity extends RxActivity implements HasFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return fragmentInjector;
    }
}
