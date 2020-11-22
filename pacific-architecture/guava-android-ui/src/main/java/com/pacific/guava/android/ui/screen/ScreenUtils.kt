package com.pacific.guava.android.ui.screen

import android.content.res.Resources
import android.graphics.Point
import kotlin.math.max
import kotlin.math.min

val screen: Point by lazy {
    val dm = Resources.getSystem().displayMetrics
    return@lazy Point(max(dm.widthPixels, dm.heightPixels), min(dm.widthPixels, dm.heightPixels))
}