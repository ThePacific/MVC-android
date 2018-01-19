package com.pacific.arch_test_kotlin.di;

import com.pacific.arch_test_kotlin.MainActivity;
import com.pacific.arch_test_kotlin.SecondActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector
    public abstract MainActivity mainActivity();

    @ContributesAndroidInjector
    public abstract SecondActivity secondActivity();
}