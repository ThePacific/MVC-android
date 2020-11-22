package com.pacific.guava.android.context

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.getSystemService
import androidx.core.content.res.ResourcesCompat
import com.pacific.guava.jvm.math.MathUtils

fun Context.putTextToClipboard(text: String) {
    val manager: ClipboardManager = getSystemService()!!
    manager.setPrimaryClip(ClipData.newPlainText(null, text))
}

fun Context.getTextFromClipboard(): String? {
    val manager: ClipboardManager = getSystemService()!!
    val clip = manager.primaryClip
    if (clip != null && clip.itemCount > 0) {
        return clip.getItemAt(0).text?.toString()
    }
    return null
}

fun Context.spValue(@DimenRes id: Int): Float {
    return MathUtils.divide(resources.getDimension(id), resources.displayMetrics.scaledDensity)
}

fun Context.dpValue(@DimenRes id: Int): Float {
    return MathUtils.divide(resources.getDimension(id), resources.displayMetrics.density)
}

fun Context.dp2px(dpValue: Float): Float {
    return MathUtils.multiply(dpValue, resources.displayMetrics.density)
}

fun Context.px2dp(pxValue: Float): Float {
    return MathUtils.divide(pxValue, resources.displayMetrics.density)
}

fun Context.sp2px(spValue: Float): Float {
    return MathUtils.multiply(spValue, resources.displayMetrics.scaledDensity)
}

fun Context.px2sp(pxValue: Float): Float {
    return MathUtils.divide(pxValue, resources.displayMetrics.scaledDensity)
}

fun Context.dp2px2(dpValue: Float): Int {
    return (dp2px(dpValue) + 0.5f).toInt()
}

fun Context.px2dp2(pxValue: Float): Int {
    return (px2dp(pxValue) + 0.5f).toInt()
}

fun Context.sp2px2(spValue: Float): Int {
    return (sp2px(spValue) + 0.5f).toInt()
}

fun Context.px2sp2(pxValue: Float): Int {
    return (px2sp(pxValue) + 0.5f).toInt()
}

@ColorInt
@SuppressWarnings("deprecation")
fun Context.toColor(@ColorRes colorRes: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getColor(colorRes, theme)
    } else {
        resources.getColor(colorRes)
    }
}

fun Context.toColorDrawable(@ColorRes id: Int) = ColorDrawable(toColor(id))

fun Context.dimension(@DimenRes id: Int) = resources.getDimension(id)

fun Context.dimensionPixelSize(@DimenRes id: Int) = resources.getDimensionPixelSize(id)

fun Context.dimensionPixelOffset(@DimenRes id: Int) = resources.getDimensionPixelOffset(id)

fun Context.toDrawable(@DrawableRes drawableRes: Int): Drawable {
    return ResourcesCompat.getDrawable(resources, drawableRes, theme)!!
}