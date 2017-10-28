package com.pacific.presentation.core;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import dagger.android.support.DaggerAppCompatActivity;
import io.reactivex.internal.functions.ObjectHelper;
import javax.annotation.CheckForNull;
import javax.inject.Inject;

public abstract class Activity extends DaggerAppCompatActivity {

  @Inject
  protected OkReceiver okReceiver;
  @Inject
  protected ViewModelFactory modelFactory;
  private ViewModel realViewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    IntentFilter filter = new IntentFilter();
    addBroadcastAction(filter);
    LocalBroadcastManager.getInstance(this).registerReceiver(okReceiver, filter);
    realViewModel = ViewModelProviders.of(this, modelFactory).get(modelClass());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    okReceiver.clearConsumer();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(okReceiver);
  }

  @CallSuper
  protected void addBroadcastAction(IntentFilter filter) {
    if (applyFinishAction()) {
      okReceiver.addConsumer(filter, OkReceiver.FINISH_ACTION, ((i, ii) -> finish()));
    }
  }

  @CheckForNull
  protected final <T extends ViewModel> T fetchViewModel() {
    return (T) ObjectHelper.requireNonNull(realViewModel, "realViewModel = null");
  }

  public boolean applyFinishAction() {
    return true;
  }

  protected abstract Class<? extends ViewModel> modelClass();
}
