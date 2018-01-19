package com.pacific.arch_test_kotlin;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

/**
 * Created by Android Open Source Project.
 */

public class TestService extends Service {
    public static void start(Context context) {
        Intent service = new Intent(context, TestService.class);
        ContextCompat.startForegroundService(context, service);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
