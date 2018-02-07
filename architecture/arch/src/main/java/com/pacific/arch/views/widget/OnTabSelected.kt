package com.pacific.arch.views.widget

import android.support.design.widget.TabLayout

abstract class OnTabSelected : TabLayout.OnTabSelectedListener {
    override fun onTabUnselected(tab: TabLayout.Tab) {}

    override fun onTabReselected(tab: TabLayout.Tab) {}
}
