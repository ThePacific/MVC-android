package com.pacific.guava.android.ui.view.recyclerview

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

fun RecyclerView.disableDefaultItemAnimator() {
    this.itemAnimator?.let {
        if (it is DefaultItemAnimator) {
            it.supportsChangeAnimations = false
        }
    }
}

fun RecyclerView.keepItemViewVisible(position: Int) {
    if (position < 0) return
    this.layoutManager?.let {
        val itemView = it.findViewByPosition(position)
        if (itemView == null) {
            this.scrollToPosition(position)
            return
        }
        if (it.isViewPartiallyVisible(itemView, false, true)) {
            this.scrollToPosition(position)
        }
    }
}

@JvmOverloads
fun SwipeRefreshLayout.cancelRefreshing(delayMillis: Long = 500L) {
    if (this.isRefreshing) {
        this.postDelayed(
                { isRefreshing = false },
                delayMillis
        )
    }
}

fun SwipeRefreshLayout.startRefreshing() {
    if (!this.isRefreshing) {
        this.isRefreshing = true
    }
}