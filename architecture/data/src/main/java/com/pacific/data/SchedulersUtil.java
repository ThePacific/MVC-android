package com.pacific.data;

import android.os.Looper;

import io.reactivex.android.MainThreadDisposable;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class SchedulersUtil {
    private SchedulersUtil() {
        throw new UnsupportedOperationException();
    }

    public static boolean isMainThread() {
        try {
            return Looper.myLooper() == Looper.getMainLooper();
        } catch (Exception e) {
            return true;// Cover for tests
        }
    }

    public static void verifyMainThread() {
        if (BuildConfig.DEBUG) {
            return;// Cover for tests
        }
        MainThreadDisposable.verifyMainThread();
    }

    public static void verifyWorkThread() {
        if (BuildConfig.DEBUG) {
            return;// Cover for tests
        }
        if (isMainThread()) {
            throw new UnsupportedOperationException("Can't run in Main thread");
        }
    }

    public static void postToMainThread(Runnable runnable) {
        AndroidSchedulers.mainThread().scheduleDirect(runnable);
    }
}
