package com.pacific.guava.android.ui.screen

import android.content.res.Resources
import android.graphics.Point

/**
 * 手机屏幕分辨率（px），不含系统状态栏
 */
val screen: Point by lazy {
    val dm = Resources.getSystem().displayMetrics
    return@lazy Point(dm.widthPixels, dm.heightPixels)
}