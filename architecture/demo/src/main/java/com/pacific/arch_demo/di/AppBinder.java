package com.pacific.arch_demo.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;

import com.pacific.arch_demo.App;
import com.pacific.arch_demo.BottomSheet;
import com.pacific.presentation.core.ViewModelFactory;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AppBinder {
    @Singleton
    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory __);

    @Singleton
    @Binds
    public abstract Application provideApplication(App __);

    @ContributesAndroidInjector
    public abstract BottomSheet bottomSheetDialogFragment();
}
