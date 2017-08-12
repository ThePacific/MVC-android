package com.thepacific.clean.di;

import android.arch.lifecycle.ViewModel;

import com.thepacific.clean.MainViewModel;
import com.thepacific.clean.SecondViewModel;
import com.thepacific.presentation.id.ViewModelKey;
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
