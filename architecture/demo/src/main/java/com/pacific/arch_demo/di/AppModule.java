package com.pacific.arch_demo.di;

import dagger.Module;

@Module(includes = {AppBinder.class, ViewModelModule.class})
public class AppModule {

}
