package com.pacific.guava.android.context;

import static android.content.pm.PackageManager.GET_SERVICES;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.pacific.guava.android.SdkUtilsKt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;

public final class ServiceUtils {

    private ServiceUtils() {
    }

    /**
     * 是否在某进程中
     *
     * @param context
     * @param serviceClass 进程
     * @return
     */
    public static boolean isInServiceProcess(
            @NonNull Context context,
            @NonNull Class<? extends Service> serviceClass
    ) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), GET_SERVICES);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        String mainProcess = packageInfo.applicationInfo.processName;

        ComponentName component = new ComponentName(context, serviceClass);
        ServiceInfo serviceInfo;
        try {
            serviceInfo = packageManager.getServiceInfo(component, 0);
        } catch (PackageManager.NameNotFoundException ignored) {
            // Service is disabled.
            return false;
        }

        if (serviceInfo.processName.equals(mainProcess)) {
            // Technically we are in the service process, but we're not in the service dedicated process.
            return false;
        }

        int myPid = android.os.Process.myPid();
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningAppProcessInfo myProcess = null;
        List<ActivityManager.RunningAppProcessInfo> runningProcesses;
        try {
            runningProcesses = activityManager.getRunningAppProcesses();
        } catch (SecurityException exception) {
            // https://github.com/square/leakcanary/issues/948
            exception.printStackTrace();
            return false;
        }
        if (runningProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo process : runningProcesses) {
                if (process.pid == myPid) {
                    myProcess = process;
                    break;
                }
            }
        }
        if (myProcess == null) {
            return false;
        }

        return myProcess.processName.equals(serviceInfo.processName);
    }

    /**
     * 获取进程名字
     *
     * @param context
     * @return
     */
    @Nullable
    public static String getProcessName(@NonNull Context context) {
        return getProcessName(context, android.os.Process.myPid());
    }

    /**
     * 获取进程名字
     *
     * @param context
     * @param pid
     * @return
     */
    @Nullable
    public static String getProcessName(@NonNull Context context, int pid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        if (processInfoList == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : processInfoList) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return null;
    }

    /**
     * 获取当前进程名字
     *
     * @return
     */
    @Nullable
    @WorkerThread
    public static String getProcessName() {
        SdkUtilsKt.ensureWorkThread();
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
