package com.pacific.arch_demo;

import com.pacific.arch_demo.di.DaggerAppComponent;
import com.pacific.common.SystemUtil;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerAppComponent.builder().create(this);
  }

  @Override
  public void onCreate() {
    super.onCreate();
    SystemUtil.attachDebug(this, null);
  }
}
