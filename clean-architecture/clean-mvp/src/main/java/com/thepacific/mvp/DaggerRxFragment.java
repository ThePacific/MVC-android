package com.thepacific.mvp;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.thepacific.dagger.AndroidInjection;
import com.thepacific.dagger.AndroidInjector;
import com.thepacific.dagger.DispatchingAndroidInjector;
import com.thepacific.dagger.HasFragmentInjector;

import javax.inject.Inject;

public class DaggerRxFragment extends RxFragment implements HasFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> childFragmentInjector;

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public AndroidInjector<Fragment> fragmentInjector() {
        return childFragmentInjector;
    }
}
