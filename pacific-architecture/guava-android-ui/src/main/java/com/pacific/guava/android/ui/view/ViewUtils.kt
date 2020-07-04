package com.pacific.guava.android.ui.view

import android.app.Activity
import android.graphics.Point
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun View.setEnableView(enabled: Boolean) {
    this.isEnabled = enabled
    if (this is ViewGroup) {
        for (idx in 0 until this.childCount) {
            this.getChildAt(idx).setEnableView(enabled)
        }
    }
}

fun View.setEnableChildView(enabled: Boolean) {
    if (this is ViewGroup) {
        for (idx in 0 until this.childCount) {
            this.getChildAt(idx).setEnableView(enabled)
        }
    }
}

fun View.toMeasure(): Point {
    var lp: ViewGroup.LayoutParams? = this.layoutParams
    if (lp == null) {
        lp = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }
    val widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width)
    val lpHeight = lp.height
    val heightSpec: Int
    heightSpec = if (lpHeight > 0) {
        View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY)
    } else {
        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    }
    this.measure(widthSpec, heightSpec)
    return Point(this.measuredWidth, this.measuredHeight)
}

/**
 * An extension which performs an [action] if the view has been measured, otherwise waits for it to
 * be measured, and then performs the [action].
 */
inline fun View.measured(crossinline action: () -> Unit) {
    if (isLaidOut) {
        action()
    } else {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
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
                removeOnLayoutChangeListener(this)
                action()
            }
        })
    }
}

/**
 * Inflate a [View] with given layoutId and attach it to the calling [ViewGroup].
 * @param layout Id of the layout to inflate.
 */
fun ViewGroup.inflateView(@LayoutRes layout: Int): View {
    return LayoutInflater.from(this.context).inflate(layout, this, false)
}

@Suppress("UNCHECKED_CAST")
fun <T : Activity> View.requireActivity(): T {
    return this.context as T
}


val View.marginLeft: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin ?: 0

val View.marginTop: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0

val View.marginRight: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.rightMargin ?: 0

val View.marginBottom: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0

val View.marginStart: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.marginStart ?: 0

val View.marginEnd: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.marginEnd ?: 0

fun View.getBounds(rect: Rect) {
    rect.set(left, top, right, bottom)
}

