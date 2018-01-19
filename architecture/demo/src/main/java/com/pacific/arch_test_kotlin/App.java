package com.pacific.arch_test_kotlin;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.util.Preconditions2;

import com.pacific.arch_test_kotlin.di.DaggerAppComponent;
import com.pacific.common.SystemUtil;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SystemUtil.attachDebug(this, null);

        Preconditions2.checkArgument(false);
    }
}
