package com.thepacific.clean;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class MainApplication extends DaggerApplication {

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerApplicationComponent.builder().create(this);
  }
}
