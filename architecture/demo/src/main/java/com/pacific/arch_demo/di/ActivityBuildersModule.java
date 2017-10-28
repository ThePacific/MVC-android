package com.pacific.arch_demo.di;

import com.pacific.arch_demo.MainActivity;
import com.pacific.arch_demo.SecondActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    public abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    public abstract SecondActivity secondActivity();
}