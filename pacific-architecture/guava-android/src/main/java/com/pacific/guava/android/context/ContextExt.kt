package com.pacific.guava.android.context

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.ColorStateList
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

/**
 * 复制
 */
fun Context.putTextToClipboard(text: String) {
    val manager: ClipboardManager = getSystemService()!!
    manager.setPrimaryClip(ClipData.newPlainText(null, text))
}

/**
 * 粘贴
 */
fun Context.getTextFromClipboard(): String? {
    val manager: ClipboardManager = getSystemService()!!
    val clip = manager.primaryClip
    if (clip != null && clip.itemCount > 0) {
        return clip.getItemAt(0).text?.toString()
    }
    return null
}

/**
 * dimen转sp
 */
fun Context.spValue(@DimenRes id: Int): Float {
    return MathUtils.divide(resources.getDimension(id), resources.displayMetrics.scaledDensity)
}

/**
 * dimen转dp
 */
fun Context.dpValue(@DimenRes id: Int): Float {
    return MathUtils.divide(resources.getDimension(id), resources.displayMetrics.density)
}

/**
 * dp转px
 */
fun Context.dp2px(dpValue: Float): Float {
    return MathUtils.multiply(dpValue, resources.displayMetrics.density)
}

/**
 * px转dp
 */
fun Context.px2dp(pxValue: Float): Float {
    return MathUtils.divide(pxValue, resources.displayMetrics.density)
}

/**
 * sp转px
 */
fun Context.sp2px(spValue: Float): Float {
    return MathUtils.multiply(spValue, resources.displayMetrics.scaledDensity)
}

/**
 * px转sp
 */
fun Context.px2sp(pxValue: Float): Float {
    return MathUtils.divide(pxValue, resources.displayMetrics.scaledDensity)
}

/**
 * dp转px
 */
fun Context.dp2px2(dpValue: Float): Int {
    return (dp2px(dpValue) + 0.5f).toInt()
}

/**
 * px转dp
 */
fun Context.px2dp2(pxValue: Float): Int {
    return (px2dp(pxValue) + 0.5f).toInt()
}

/**
 * sp转px
 */
fun Context.sp2px2(spValue: Float): Int {
    return (sp2px(spValue) + 0.5f).toInt()
}

/**
 * px转sp
 */
fun Context.px2sp2(pxValue: Float): Int {
    return (px2sp(pxValue) + 0.5f).toInt()
}

/**
 * 获取状态栏高度
 */
fun Context.getStatusBarHeight(): Int {
    val identifier = resources.getIdentifier(
        "status_bar_height", "dimen", "android"
    )
    return if (identifier > 0) resources.getDimensionPixelSize(identifier) else 0
}

/**
 * 颜色资源转颜色整数值
 */
@ColorInt
fun Context.toColor(@ColorRes id: Int): Int {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getColor(id, theme)
    } else {
        resources.getColor(id)
    }
}

/**
 * 颜色资源转颜色Drawable
 */
fun Context.toColorDrawable(@ColorRes id: Int) = ColorDrawable(toColor(id))

/**
 * dimen资源转px
 */
fun Context.dimension(@DimenRes id: Int) = resources.getDimension(id)

/**
 * dimen资源转px
 */
fun Context.dimensionPixelSize(@DimenRes id: Int) = resources.getDimensionPixelSize(id)

/**
 * dimen资源转px
 */
fun Context.dimensionPixelOffset(@DimenRes id: Int) = resources.getDimensionPixelOffset(id)

/**
 * Drawable资源转Drawable
 */
fun Context.toDrawable(@DrawableRes drawableRes: Int): Drawable {
    return ResourcesCompat.getDrawable(resources, drawableRes, theme)!!
}

/**
 * 颜色资源资源转Drawable
 */
fun Context.toColorState(@ColorRes id: Int): ColorStateList {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        resources.getColorStateList(id, theme)
    } else {
        ColorStateList.valueOf(toColor(id))
    }
}