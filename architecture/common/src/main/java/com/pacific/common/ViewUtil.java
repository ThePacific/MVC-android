package com.pacific.common;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

public class ViewUtil {

  private ViewUtil() {
    throw new UnsupportedOperationException();
  }

  public static void setPasswordMode(EditText editText, boolean isPassword) {
    if (isPassword) {
      editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
    } else {
      editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    editText.setSelection(editText.getText().toString().length());
  }

  public static void trimItemAnimator(RecyclerView view) {
    ((DefaultItemAnimator) view.getItemAnimator()).setSupportsChangeAnimations(false);
  }

  public static boolean isAttached(View view) {
    return (VERSION.SDK_INT >= VERSION_CODES.KITKAT && view.isAttachedToWindow())
        || view.getWindowToken() != null;
  }

  public static boolean onSdk(int version) {
    return VERSION.SDK_INT >= version;
  }
}
