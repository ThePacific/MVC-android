package com.pacific.presentation;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import dagger.android.support.DaggerFragment;
import io.reactivex.internal.functions.ObjectHelper;
import javax.inject.Inject;

public abstract class Fragment extends DaggerFragment {

  @Inject
  protected ViewModelFactory modelFactory;
  private ViewModel realViewModel;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (isAttachViewModel()) {
      switch (modelProvider()) {
        case ACTIVITY:
          realViewModel = ViewModelProviders.of(getActivity(), modelFactory)
              .get(modelClass());
          break;
        case PARENT_FRAGMENT:
          realViewModel = ViewModelProviders.of(getParentFragment(), modelFactory)
              .get(modelClass());
          break;
        case NONE:
          realViewModel = ViewModelProviders.of(this, modelFactory)
              .get(modelClass());
          break;
        default:
          throw new UnsupportedOperationException();
      }
    }
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (!view.isClickable()) {
      view.setClickable(true);
    }
  }

  protected ViewModelSource modelProvider() {
    return ViewModelSource.ACTIVITY;
  }

  @SuppressWarnings("unchecked")
  protected final <T extends ViewModel> T fetchViewModel() {
    return (T) ObjectHelper.requireNonNull(realViewModel, "realViewModel = null");
  }

  protected boolean isAttachViewModel() {
    return true;
  }

  protected abstract Class<? extends ViewModel> modelClass();
}
