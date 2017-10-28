package com.thepacific.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyboardUtil {

  private KeyboardUtil() {
    throw new UnsupportedOperationException();
  }

  public static void showKeyboard(Context context, EditText target) {
    if (context == null || target == null) {
      return;
    }
    InputMethodManager imm = (InputMethodManager) context
        .getSystemService(Context.INPUT_METHOD_SERVICE);
    imm.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT);
  }

  public static void showKeyboardInDialog(Dialog dialog, EditText target) {
    if (dialog == null || target == null) {
      return;
    }
    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    target.requestFocus();
  }

  public static void hideKeyboard(Context context, View target) {
    if (context == null || target == null) {
      return;
    }
    InputMethodManager imm = (InputMethodManager) context.getSystemService(
        Context.INPUT_METHOD_SERVICE);
    imm.hideSoftInputFromWindow(target.getWindowToken(), 0);
  }

  public static void hideKeyboard(Activity activity) {
    //We can also call findViewById(android.R.id.content)
    View target = activity.getWindow().getDecorView();
    hideKeyboard(activity, target);
  }
}
