package com.square.common.ui.screen

import android.content.res.Resources
import android.graphics.Point

val screen: Point by lazy {
    val dm = Resources.getSystem().displayMetrics
    return@lazy Point(dm.widthPixels, dm.heightPixels)
}