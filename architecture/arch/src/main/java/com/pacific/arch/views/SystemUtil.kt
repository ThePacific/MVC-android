package com.pacific.arch.views

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.graphics.Point
import android.os.StrictMode
import android.telephony.TelephonyManager
import android.util.Log
import com.pacific.arch.rx.CompletableUtil
import com.squareup.leakcanary.LeakCanary
import io.reactivex.Completable

const val ARMEABI = 1
const val ARMEABI_V7 = 2
const val ARM64_V8A = 3
const val X86 = 4
const val X86_64 = 5
const val MIPS = 6
const val MIPS_64 = 7

fun getCupArch(): Int {
    val arch = System.getProperty("os.arch").toLowerCase()
    if (arch.contains("mip")) {
        return if (arch.contains("64")) {
            MIPS_64
        } else {
            MIPS
        }
    }
    if (arch.contains("86")) {
        return if (arch.contains("64")) {
            X86_64
        } else {
            X86
        }
    }
    if (arch.contains("ar")) {
        return if (arch.contains("64")) {
            ARM64_V8A
        } else if (arch.contains("7")) {
            ARMEABI_V7
        } else {
            ARMEABI
        }
    }
    throw AssertionError("Unknown cpu arch")
}

fun getCupArchDescription(): String {
    when (getCupArch()) {
        ARMEABI -> return "armeabi"
        ARMEABI_V7 -> return "armeabi_v7"
        ARM64_V8A -> return "arm64_v8a"
        X86 -> return "x86"
        X86_64 -> return "x86_64"
        MIPS -> return "mips"
        MIPS_64 -> return "mips_64"
        else -> return "Unknown CUP"
    }
}

fun isEmulator(context: Context): Boolean {
    val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    val networkOperator = tm.networkOperatorName.toLowerCase()
    return "android" == networkOperator
}

fun getBuildConfigValue(context: Context, key: String): Any? {
    try {
        val clazz = Class.forName(context.packageName + ".BuildConfig")
        val field = clazz.getField(key)
        return field.get(null)
    } catch (e: ClassNotFoundException) {
        Log.d("SystemUtil", "Unable to get the BuildConfig, is this built with ANT?")
        e.printStackTrace()
    } catch (e: NoSuchFieldException) {
        Log.d("SystemUtil", key + " is not a valid field. Check your build.gradle")
    } catch (e: IllegalAccessException) {
        Log.d("SystemUtil", "Illegal Access Exception: Let's print a stack trace.")
        e.printStackTrace()
    }

    return null
}

fun attachDebug(application: Application, runnable: Runnable?) {
    Completable.fromAction {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return@fromAction
        }
        runnable?.run()
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build())
        StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build())
        LeakCanary.install(application)
    }
            .compose(CompletableUtil.newThread())
            .subscribe()
}

fun screenSize(): Point {
    val width = Resources.getSystem().displayMetrics.widthPixels
    val height = Resources.getSystem().displayMetrics.heightPixels
    return Point(width, height)
}