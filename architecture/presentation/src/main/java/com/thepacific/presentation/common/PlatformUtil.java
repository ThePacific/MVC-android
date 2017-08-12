package com.thepacific.presentation.common;

import android.os.Build;

public class PlatformUtil {

  private PlatformUtil() {
    throw new UnsupportedOperationException();
  }

  public static boolean ICE_CREAM_SANDWICH() {//14
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  public static boolean ICE_CREAM_SANDWICH_MR1() {//15
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;
  }

  public static boolean JELLY_BEAN() {//16
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
  }

  public static boolean JELLY_BEAN_MR1() {//17
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
  }

  public static boolean JELLY_BEAN_MR2() {//18
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
  }

  public static boolean KITKAT() {//19
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
  }

  public static boolean KITKAT_WATCH() {//20
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
  }

  public static boolean LOLLIPOP() {//21
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
  }

  public static boolean LOLLIPOP_MR1() {//22
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
  }

  public static boolean M() {//23
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
  }

  public static boolean N() {//24
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
  }

  public static boolean N_MR1() {//25
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1;
  }

  public static boolean O() {//26
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
  }
}
