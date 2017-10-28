package com.thepacific.clean;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.thepacific.presentation.common.RouterUtil;
import com.thepacific.presentation.core.BottomSheetDialogFragment;
import com.thepacific.presentation.core.ViewModelSource;

public class BottomSheet extends BottomSheetDialogFragment {

  private SecondViewModel model;

  public BottomSheet() {
  }

  public static BottomSheet newInstance() {
    Bundle args = new Bundle();
    BottomSheet fragment = new BottomSheet();
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
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    view.findViewById(R.id.text).setOnClickListener(i -> RouterUtil.dismiss(this));
  }

  @Override
  protected ViewModelSource modelProvider() {
    return ViewModelSource.ACTIVITY;
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
