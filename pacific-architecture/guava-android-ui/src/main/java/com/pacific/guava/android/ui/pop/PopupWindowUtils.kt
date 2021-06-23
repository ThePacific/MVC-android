package com.pacific.guava.android.ui.pop

import android.app.Dialog
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.widget.PopupWindow
import androidx.annotation.DrawableRes
import com.pacific.guava.android.verifySdk

/**
 * 显示pop，适配android 7+显示问题
 *
 * @param anchor 锚点
 * @param xOff x方向平移
 * @param yOff y方向平移
 * @param gravity 方向，left, top, right, bottom
 *
 */
fun PopupWindow.showAsDropDownAndroidN(anchor: View, xOff: Int, yOff: Int, gravity: Int) {
    // android 7+ pop锚点计算多出可视范围高度，需要矫正pop可视高度
    if (verifySdk(Build.VERSION_CODES.N)) {
        val visibleFrame = Rect()// 可视范围高度
        anchor.getGlobalVisibleRect(visibleFrame)
        val height = anchor.resources.displayMetrics.heightPixels - visibleFrame.bottom
        this.height = height
        this.showAsDropDown(anchor, xOff, yOff, gravity)
    } else {
        this.showAsDropDown(anchor, xOff, yOff, gravity)
    }
}

/**
 * 设置dialog背景，默认是透明，一般用于全屏对话框
 * @param drawableRes 对应R资源
 */
@JvmOverloads
fun Dialog.applyBackgroundDrawableResource(
    @DrawableRes drawableRes: Int = android.R.color.transparent
) {
    window?.setBackgroundDrawableResource(drawableRes)
}