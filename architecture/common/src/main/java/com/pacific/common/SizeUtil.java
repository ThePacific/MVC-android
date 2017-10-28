package com.pacific.common;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.DimenRes;
import android.view.View;
import android.view.ViewGroup;

public class SizeUtil {

  private SizeUtil() {
    throw new UnsupportedOperationException();
  }

  public static float xmlDP(Context context, @DimenRes int id) {
    Resources resources = context.getResources();
    return resources.getDimension(id) / resources.getDisplayMetrics().density;
  }

  public static float dp2px(Context context, float dpValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return dpValue * scale;
  }

  public static float px2dp(Context context, float pxValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return pxValue / scale;
  }

  public static float sp2px(Context context, float spValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return spValue * fontScale;
  }

  public static float px2sp(Context context, float pxValue) {
    final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
    return pxValue / fontScale;
  }

  public static int dp2px_(Context context, float dpValue) {
    return (int) (dp2px(context, dpValue) + 0.5f);
  }

  public static int px2dp_(Context context, float pxValue) {
    return (int) (px2dp(context, pxValue) + 0.5f);
  }

  public static int sp2px_(Context context, float spValue) {
    return (int) (sp2px(context, spValue) + 0.5f);
  }

  public static int px2sp_(Context context, float pxValue) {
    return (int) (px2sp(context, pxValue) + 0.5f);
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
}
