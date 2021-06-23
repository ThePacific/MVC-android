package com.pacific.guava.android.mvvm

import android.widget.Toast
import androidx.annotation.StringRes
import java.lang.ref.WeakReference

object Msg1 {

    private var weakToast: WeakReference<Toast?>? = null

    /**
     * toast = 单例 + LENGTH_SHORT
     */
    fun toast(msg: String) {
        weakToast?.get()?.cancel()
        Toast.makeText(AndroidX.myApp, msg, Toast.LENGTH_SHORT).also {
            it.show()
            weakToast = WeakReference(it)
        }
    }

    /**
     * toast = 单例 + LENGTH_SHORT
     */
    fun toast(@StringRes msg: Int) {
        weakToast?.get()?.cancel()
        Toast.makeText(AndroidX.myApp, msg, Toast.LENGTH_SHORT).also {
            it.show()
            weakToast = WeakReference(it)
        }
    }

    /**
     * 单例 + LENGTH_LONG
     */
    fun toast2(msg: String) {
        weakToast?.get()?.cancel()
        Toast.makeText(AndroidX.myApp, msg, Toast.LENGTH_LONG).also {
            it.show()
            weakToast = WeakReference(it)
        }
    }

    /**
     * 单例 + LENGTH_LONG
     */
    fun toast2(@StringRes msg: Int) {
        weakToast?.get()?.cancel()
        Toast.makeText(AndroidX.myApp, msg, Toast.LENGTH_LONG).also {
            it.show()
            weakToast = WeakReference(it)
        }
    }

    /**
     * LENGTH_SHORT
     */
    fun toast3(msg: String) {
        Toast.makeText(AndroidX.myApp, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * LENGTH_SHORT
     */
    fun toast3(@StringRes msg: Int) {
        Toast.makeText(AndroidX.myApp, msg, Toast.LENGTH_SHORT).show()
    }

    /**
     * LENGTH_LONG
     */
    fun toast4(msg: String) {
        Toast.makeText(AndroidX.myApp, msg, Toast.LENGTH_LONG).show()
    }

    /**
     * LENGTH_LONG
     */
    fun toast4(@StringRes msg: Int) {
        Toast.makeText(AndroidX.myApp, msg, Toast.LENGTH_LONG).show()
    }
}
