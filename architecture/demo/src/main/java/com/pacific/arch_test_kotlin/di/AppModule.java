package com.pacific.arch_test_kotlin.di;

import dagger.Module;

@Module(includes = {AppBinder.class, ViewModelModule.class})
public class AppModule {

}