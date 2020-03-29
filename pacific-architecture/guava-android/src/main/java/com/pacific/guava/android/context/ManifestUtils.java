package com.pacific.guava.android.context;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.pacific.guava.domain.AssertUtilsKt;

public class ManifestUtils {

    private ManifestUtils() {
        AssertUtilsKt.unsupportedOperationException("");
    }

    public static String getString(Context context, String key, String defValue) {
        Object metaData = get(context, key);
        if (metaData instanceof String) {
            return (String) metaData;
        }
        return defValue;
    }

    public static String getString(Context context, String key) {
        Object metaData = get(context, key);
        if (metaData instanceof String) {
            return (String) metaData;
        }
        return null;
    }

    public static int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    public static int getInt(Context context, String key, int defValue) {
        Object metaData = get(context, key);
        if (metaData instanceof Integer) {
            return (int) metaData;
        }
        return defValue;
    }

    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defValue) {
        Object metaData = get(context, key);
        if (metaData instanceof Boolean) {
            return (boolean) metaData;
        }
        return defValue;
    }

    private static Object get(Context context, String key) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(),
                    PackageManager.GET_META_DATA
            );
            if (null != info && null != info.metaData) {
                return info.metaData.get(key);
            } else {
                return null;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}