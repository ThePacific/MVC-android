package com.thepacific.presentation.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

public class SizeUtil {

  private SizeUtil() {
    throw new UnsupportedOperationException();
  }

  public static int dp2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  public static float px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return pxValue / scale + 0.5f;
  }

  public static int sp2px(Context context, float spValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return (int) (spValue * fontScale + 0.5f);
  }

  public static float px2sp(Context context, float pxValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return pxValue / fontScale + 0.5f;
  }

  public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
    switch (unit) {
      case TypedValue.COMPLEX_UNIT_PX:
        return value;
      case TypedValue.COMPLEX_UNIT_DIP:
        return value * metrics.density;
      case TypedValue.COMPLEX_UNIT_SP:
        return value * metrics.scaledDensity;
      case TypedValue.COMPLEX_UNIT_PT:
        return value * metrics.xdpi * (1.0f / 72);
      case TypedValue.COMPLEX_UNIT_IN:
        return value * metrics.xdpi;
      case TypedValue.COMPLEX_UNIT_MM:
        return value * metrics.xdpi * (1.0f / 25.4f);
    }
    return 0;
  }

  public static void forceGetViewSize(final View view, final onGetSizeListener listener) {
    view.post(() -> {
      if (listener != null) {
        listener.onGetSize(view);
      }
    });
  }

  public static int[] measureView(View view) {
    ViewGroup.LayoutParams lp = view.getLayoutParams();
    if (lp == null) {
      lp = new ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.WRAP_CONTENT
      );
    }
    int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
    int lpHeight = lp.height;
    int heightSpec;
    if (lpHeight > 0) {
      heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
    } else {
      heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
    }
    view.measure(widthSpec, heightSpec);
    return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
  }

  public static int getMeasuredWidth(View view) {
    return measureView(view)[0];
  }

  public static int getMeasuredHeight(View view) {
    return measureView(view)[1];
  }

  public interface onGetSizeListener {

    void onGetSize(View view);
  }
}
