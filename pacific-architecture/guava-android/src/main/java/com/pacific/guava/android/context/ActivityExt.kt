package com.pacific.guava.android.context

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.pacific.guava.android.ime.ImeUtils
import android.content.pm.ActivityInfo
import android.view.WindowManager
import androidx.annotation.ColorRes

fun FragmentActivity.instantiate(className: String): Fragment {
    return supportFragmentManager.fragmentFactory.instantiate(classLoader, className)
}

fun Activity.hideSoftInput() = ImeUtils.hideIme(this.window.decorView)

fun Activity.requestFullScreenWithLandscape() {
    with(window) {
        val wmLayoutParams = this.attributes
        wmLayoutParams.flags = wmLayoutParams.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
        attributes = wmLayoutParams
        addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
    if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }
}

fun Activity.requestNormalScreenWithPortrait() {
    with(window) {
        val wmLayoutParams = this.attributes
        wmLayoutParams.flags = wmLayoutParams.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        attributes = wmLayoutParams
        clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
    if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}

fun Activity.isLandscape(): Boolean = requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

fun Activity.applyStatusBarColor(@ColorRes colorRes: Int) {
    window.statusBarColor = this.toColor(colorRes)
}

fun Activity.applyNavigationBarColor(@ColorRes colorRes: Int) {
    window.navigationBarColor = this.toColor(colorRes)
}