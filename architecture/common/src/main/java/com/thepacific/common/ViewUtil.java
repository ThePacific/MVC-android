package com.thepacific.common;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class ViewUtil {

  private ViewUtil() {
  }

  public static boolean isMainThread() {
    try {
      return Looper.myLooper() == Looper.getMainLooper();
    } catch (Exception e) {
      // Cover for tests
      return true;
    }
  }

  public static void setPasswordMode(EditText view, boolean isPasswordMode) {
    if (isPasswordMode) {
      view.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    } else {
      view.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    view.setSelection(view.getText().toString().length());
  }

  public static boolean isAttached(View view) {
    return (VERSION.SDK_INT >= VERSION_CODES.KITKAT && view.isAttachedToWindow())
        || view.getWindowToken() != null;
  }
}
