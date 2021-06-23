package com.pacific.guava.android.ui.view.recyclerview

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

/**
 * 在ViewPager2中使用，减少华东冲突
 */
fun RecyclerView.enforceSingleScrollDirection() {
    val enforcer = SingleScrollDirectionEnforcer()
    addOnItemTouchListener(enforcer)
    addOnScrollListener(enforcer)
}

/**
 * 禁用默认列表动画
 */
fun RecyclerView.disableDefaultItemAnimator() {
    this.itemAnimator?.let {
        if (it is DefaultItemAnimator) {
            it.supportsChangeAnimations = false
        } else if (it is SimpleItemAnimator) {
            it.supportsChangeAnimations = false
        }
    }
}

/**
 * 滚动到具体哪个item
 */
fun RecyclerView.keepItemViewVisible(position: Int, smoothScroll: Boolean) {
    if (position < 0) return
    this.layoutManager?.let {
        val itemView = it.findViewByPosition(position)
        if (itemView == null) {
            this.scrollToPosition(position)
            return
        }
        if (it.isViewPartiallyVisible(itemView, false, true)) {
            if (smoothScroll) {
                this.smoothScrollToPosition(position)
            } else {
                this.scrollToPosition(position)
            }
        }
    }
}

/**
 * 取消下拉刷新状态
 */
fun SwipeRefreshLayout.cancelRefreshing(delayMillis: Long = 500L) {
    if (this.isRefreshing) {
        if (delayMillis == 0L) {
            this.isRefreshing = false
        } else {
            this.postDelayed({ isRefreshing = false }, delayMillis)
        }
    }
}