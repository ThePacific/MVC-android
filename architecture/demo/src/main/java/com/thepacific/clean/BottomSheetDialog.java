package com.thepacific.clean;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thepacific.presentation.core.BottomSheetDialogFragment;
import com.thepacific.presentation.core.ViewModel;

public class BottomSheetDialog extends BottomSheetDialogFragment {

  private SecondViewModel model;

  public BottomSheetDialog() {
  }

  public static BottomSheetDialog newInstance() {
    Bundle args = new Bundle();
    BottomSheetDialog fragment = new BottomSheetDialog();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    model = fetchViewModel();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.bottom_sheet, container, false);
  }

  @Override
  protected ViewModel.Provider modelProvider() {
    return ViewModel.Provider.ACTIVITY;
  }

  @Override
  protected boolean isAttachViewModel() {
    return true;
  }

  @Override
  protected Class<? extends ViewModel> modelClass() {
    return SecondViewModel.class;
  }
}
