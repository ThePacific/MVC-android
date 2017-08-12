package com.thepacific.data.common;

import android.os.Looper;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class ExecutorUtil {

  private ExecutorUtil() {
    throw new UnsupportedOperationException();
  }

  public static boolean isMainThread() {
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }

  public static void requireMainThread() {
    if (!isMainThread()) {
      throw new UnsupportedOperationException("Must run in Main thread");
    }
  }

  public static void requireWorkThread() {
    if (isMainThread()) {
      throw new UnsupportedOperationException("Can't run in Main thread");
    }
  }

  public static void postToMainThread(Runnable runnable) {
    AndroidSchedulers.mainThread().scheduleDirect(runnable);
  }
}
