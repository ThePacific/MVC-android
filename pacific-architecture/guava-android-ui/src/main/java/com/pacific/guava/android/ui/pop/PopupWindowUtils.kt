package com.pacific.guava.android.ui.pop

import android.app.Dialog
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.widget.PopupWindow
import androidx.annotation.DrawableRes
import com.pacific.guava.android.verifySdk

fun PopupWindow.showAsDropDownAndroidN(anchor: View, xOff: Int, yOff: Int, gravity: Int) {
    if (verifySdk(Build.VERSION_CODES.N)) {
        val visibleFrame = Rect()
        anchor.getGlobalVisibleRect(visibleFrame)
        val height = anchor.resources.displayMetrics.heightPixels - visibleFrame.bottom
        this.height = height
        this.showAsDropDown(anchor, xOff, yOff, gravity)
    } else {
        this.showAsDropDown(anchor, xOff, yOff, gravity)
    }
}

@JvmOverloads
fun Dialog.applyBackgroundDrawableResource(
        @DrawableRes DrawableRes: Int = android.R.color.transparent
) {
    window?.setBackgroundDrawableResource(DrawableRes)
}