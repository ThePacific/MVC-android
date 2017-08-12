package com.thepacific.clean.di;

import com.thepacific.clean.MainActivity;
import com.thepacific.clean.SecondActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    public abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    public abstract SecondActivity secondActivity();
}