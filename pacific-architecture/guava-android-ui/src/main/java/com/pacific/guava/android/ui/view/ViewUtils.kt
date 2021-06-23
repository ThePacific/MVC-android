package com.pacific.guava.android.ui.view

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

/**
 * 设置子view是否可用，包含ViewGroup自己
 *
 * @param enabled 是否可用
 */
fun View.setEnableView(enabled: Boolean) {
    this.isEnabled = enabled
    if (this is ViewGroup) {// 遍历
        for (idx in 0 until this.childCount) {
            this.getChildAt(idx).setEnableView(enabled)
        }
    }
}

/**
 * 设置子view是否可用，除了ViewGroup自己
 *
 * @param enabled 是否可用
 */
fun View.setEnableChildView(enabled: Boolean) {
    if (this is ViewGroup) {// 遍历
        for (idx in 0 until this.childCount) {
            this.getChildAt(idx).setEnableView(enabled)
        }
    }
}

/**
 * 提前获取view的测量尺寸
 *
 * @return x=宽，y=高
 */
fun View.toMeasure(): Point {
    var lp: ViewGroup.LayoutParams? = this.layoutParams
    if (lp == null) {// 获取布局参数
        lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)// 获取期望宽
    val lpHeight = lp.height// 获取期望高

    val heightSpec: Int = if (lpHeight > 0) {
        View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)// 固定尺寸
    } else {
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)// 约束尺寸
    }
    this.measure(widthSpec, heightSpec)// 开始测量
    return Point(this.measuredWidth, this.measuredHeight)
}

/**
 * View尺寸变更回调
 *
 * @param action 回调ludam
 */
inline fun View.onLayoutChange(crossinline action: () -> Unit) {
    if (isLaidOut) {// 如果已经显示，则直接调用，否者监听
        action()
    } else {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            // 增加回调
            override fun onLayoutChange(
                view: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                // 移除回调
                removeOnLayoutChangeListener(this)
                action()
            }
        })
    }
}

/**
 * 渲染一个布局[View]并增添到当前[ViewGroup].
 * @param layout 布局id
 */
fun ViewGroup.inflateView(@LayoutRes layout: Int): View {
    return LayoutInflater.from(this.context).inflate(layout, this, false)
}

/**
 * 获取当前Activity
 */
@Suppress("UNCHECKED_CAST")
fun <T : Activity> View.requireActivity(): T {
    return this.context as T
}


/**
 * 扩展属性，获取marginLeft
 */
val View.marginLeft: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin ?: 0

/**
 * 扩展属性，获取marginTop
 */
val View.marginTop: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0

/**
 * 扩展属性，获取marginRight
 */
val View.marginRight: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin ?: 0

/**
 * 扩展属性，获取marginBottom
 */
val View.marginBottom: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0

/**
 * 扩展属性，获取marginStart
 */
val View.marginStart: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.marginStart ?: 0

/**
 * 扩展属性，获取marginEnd
 */
val View.marginEnd: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.marginEnd ?: 0

/**
 * 获取view可视范围
 *
 * @param rect 矩形
 */
fun View.getBounds(rect: Rect) {
    rect.set(left, top, right, bottom)
}

