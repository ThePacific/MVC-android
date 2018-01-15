package com.pacific.presentation;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;

public abstract class BottomSheetDialogFragment extends AppCompatDialogFragment {

  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    return new BottomSheetDialog(getContext(), getTheme());
  }
}