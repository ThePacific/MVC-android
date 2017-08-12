package com.thepacific.presentation.core;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.internal.functions.ObjectHelper;
import javax.annotation.CheckForNull;
import javax.inject.Inject;

public abstract class Activity extends DaggerAppCompatActivity implements LifecycleRegistryOwner {

  private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
  private ViewModel realViewModel;

  @Inject
  protected OkReceiver okReceiver;

  @Inject
  protected ViewModelFactory modelFactory;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    IntentFilter filter = new IntentFilter();
    addBroadcastAction(filter);
    registerReceiver(okReceiver, filter);
    realViewModel = ViewModelProviders.of(this, modelFactory).get(modelClass());
    lifecycleRegistry.addObserver(realViewModel);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    lifecycleRegistry.removeObserver(realViewModel);
    okReceiver.clearConsumer();
    unregisterReceiver(okReceiver);
  }

  @Override
  public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
  }

  @CallSuper
  protected void addBroadcastAction(IntentFilter filter) {
    if (applyFinishAction()) {
      filter.addAction(OkAction.FINISH_ACTION);
      okReceiver.addConsumer(OkAction.FINISH_ACTION, ((i, ii) -> finish()));
    }
  }

  @CheckForNull
  protected final <T extends ViewModel> T fetchViewModel() {
    return (T) ObjectHelper.requireNonNull(realViewModel, "is null");
  }

  public boolean applyFinishAction() {
    return true;
  }

  protected abstract Class<? extends ViewModel> modelClass();
}
