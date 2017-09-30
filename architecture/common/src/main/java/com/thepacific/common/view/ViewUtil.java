package com.thepacific.common.view;

import android.text.InputType;
import android.widget.EditText;

public class ViewUtil {

  private ViewUtil() {
  }

  public static void setPasswordMode(EditText view, boolean isPasswordMode) {
    if (isPasswordMode) {
      view.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    } else {
      view.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    view.setSelection(view.getText().toString().length());
  }
}
