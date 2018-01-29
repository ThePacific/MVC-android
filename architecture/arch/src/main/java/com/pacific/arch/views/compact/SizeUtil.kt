package com.pacific.arch.views.compact

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.support.annotation.DimenRes
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun showKeyboard(context: Context?, target: EditText?) {
    if (context == null || target == null) return
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT)
}

fun showKeyboardInDialog(dialog: Dialog?, target: EditText?) {
    if (dialog == null || target == null) return
    dialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
    target.requestFocus()
}

fun hideKeyboard(context: Context?, target: View?) {
    if (context == null || target == null) return
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(target.windowToken, 0)
}

fun hideKeyboard(activity: Activity) {
    //We can also call findViewById(android.R.id.content)
    val target = activity.window.decorView
    hideKeyboard(activity, target)
}

fun getXmlDP(context: Context, @DimenRes id: Int): Float {
    val resources = context.resources
    return resources.getDimension(id) / resources.displayMetrics.density
}

fun dp2px(context: Context, dpValue: Float): Float {
    val scale = context.resources.displayMetrics.density
    return dpValue * scale
}

fun px2dp(context: Context, pxValue: Float): Float {
    val scale = context.resources.displayMetrics.density
    return pxValue / scale
}

fun sp2px(context: Context, spValue: Float): Float {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return spValue * fontScale
}

fun px2sp(context: Context, pxValue: Float): Float {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return pxValue / fontScale
}

fun dp2px_int(context: Context, dpValue: Float): Int {
    return (dp2px(context, dpValue) + 0.5f).toInt()
}

fun px2dp_int(context: Context, pxValue: Float): Int {
    return (px2dp(context, pxValue) + 0.5f).toInt()
}

fun sp2px_int(context: Context, spValue: Float): Int {
    return (sp2px(context, spValue) + 0.5f).toInt()
}

fun px2sp_int(context: Context, pxValue: Float): Int {
    return (px2sp(context, pxValue) + 0.5f).toInt()
}

fun measureView(view: View): IntArray {
    var lp: ViewGroup.LayoutParams? = view.layoutParams
    if (lp == null) {
        lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)
    val lpHeight = lp.height
    val heightSpec: Int
    heightSpec = if (lpHeight > 0) {
        View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
    } else {
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    }
    view.measure(widthSpec, heightSpec)
    return intArrayOf(view.measuredWidth, view.measuredHeight)
}

fun getMeasuredWidth(view: View): Int {
    return measureView(view)[0]
}

fun getMeasuredHeight(view: View): Int {
    return measureView(view)[1]
}