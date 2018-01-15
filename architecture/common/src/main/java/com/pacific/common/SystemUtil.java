package com.pacific.common;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.pacific.common.rx.CompletableUtil;
import com.squareup.leakcanary.LeakCanary;
import io.reactivex.Completable;
import java.lang.reflect.Field;
import javax.annotation.Nullable;

public class SystemUtil {

  public static final int ARMEABI = 1;
  public static final int ARMEABI_V7 = 2;
  public static final int ARM64_V8A = 3;
  public static final int X86 = 4;
  public static final int X86_64 = 5;
  public static final int MIPS = 6;
  public static final int MIPS_64 = 7;

  private SystemUtil() {
    throw new UnsupportedOperationException();
  }

  public static int getCupArch() {
    String arch = System.getProperty("os.arch").toLowerCase();
    if (arch.contains("mip")) {
      if (arch.contains("64")) {
        return MIPS_64;
      } else {
        return MIPS;
      }
    }
    if (arch.contains("86")) {
      if (arch.contains("64")) {
        return X86_64;
      } else {
        return X86;
      }
    }
    if (arch.contains("ar")) {
      if (arch.contains("64")) {
        return ARM64_V8A;
      } else if (arch.contains("7")) {
        return ARMEABI_V7;
      } else {
        return ARMEABI;
      }
    }
    throw new AssertionError("Unknown CUP arch");
  }

  public static String getCupArchDescription() {
    switch (getCupArch()) {
      case ARMEABI:
        return "armeabi";
      case ARMEABI_V7:
        return "armeabi_v7";
      case ARM64_V8A:
        return "arm64_v8a";
      case X86:
        return "x86";
      case X86_64:
        return "x86_64";
      case MIPS:
        return "mips";
      case MIPS_64:
        return "mips_64";
      default:
        return "Unknown CUP";
    }
  }

  public static boolean isEmulator(Context context) {
    TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    String networkOperator = tm.getNetworkOperatorName().toLowerCase();
    return "android".equals(networkOperator);
  }

  public static Object getBuildConfigValue(Context context, String key) {
    try {
      Class<?> clazz = Class.forName(context.getPackageName() + ".BuildConfig");
      Field field = clazz.getField(key);
      return field.get(null);
    } catch (ClassNotFoundException e) {
      Log.d("SystemUtil", "Unable to get the BuildConfig, is this built with ANT?");
      e.printStackTrace();
    } catch (NoSuchFieldException e) {
      Log.d("SystemUtil", key + " is not a valid field. Check your build.gradle");
    } catch (IllegalAccessException e) {
      Log.d("SystemUtil", "Illegal Access Exception: Let's print a stack trace.");
      e.printStackTrace();
    }
    return null;
  }

  public static void attachDebug(Application application, @Nullable Runnable runnable) {
    Completable.fromAction(
        () -> {
          if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
          }
          if (runnable != null) {
            runnable.run();
          }
          StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
              .detectAll()
              .penaltyLog()
              .penaltyDeath()
              .build());
          StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
              .detectAll()
              .penaltyLog()
              .penaltyDeath()
              .build());
          LeakCanary.install(application);
        })
        .compose(CompletableUtil.newThread())
        .subscribe();
  }
}
