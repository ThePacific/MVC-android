package com.pacific.guava.android.context

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.pacific.guava.android.ime.ImeUtils
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.annotation.ColorRes

/**
 * 创建Fragment
 */
fun FragmentActivity.instantiateFragment(className: String): Fragment {
    return supportFragmentManager.fragmentFactory.instantiate(classLoader, className)
}

/**
 * 隐藏软键盘
 */
fun Activity.hideSoftInput() = ImeUtils.hideIme(this.window.decorView)

/**
 * 是否横屏
 */
fun Activity.isLandscape(): Boolean = requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

/**
 * 设置状态栏颜色
 */
fun Activity.applyStatusBarColor(@ColorRes colorRes: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.statusBarColor = resources.getColor(colorRes, theme)
    } else {
        window.statusBarColor = resources.getColor(colorRes)
    }
}

/**
 * 设置导航栏栏颜色
 */
fun Activity.applyNavigationBarColor(@ColorRes colorRes: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        window.navigationBarColor = resources.getColor(colorRes, theme)
    } else {
        window.navigationBarColor = resources.getColor(colorRes)
    }
}