package com.pacific.arch_test_kotlin.di;

import android.arch.lifecycle.ViewModel;

import com.pacific.arch_test_kotlin.MainViewModel;
import com.pacific.arch_test_kotlin.SecondViewModel;
import com.pacific.presentation.ViewModelKey;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindLaunchViewModel(MainViewModel __);

    @Binds
    @IntoMap
    @ViewModelKey(SecondViewModel.class)
    public abstract ViewModel bindSecondViewModel(SecondViewModel __);
}
