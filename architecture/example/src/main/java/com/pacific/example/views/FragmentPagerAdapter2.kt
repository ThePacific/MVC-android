package com.pacific.example.views

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup

abstract class FragmentPagerAdapter2(fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager) {

    var currentPosition = -1
        private set
    var currentFragment: Fragment? = null
        private set

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        currentPosition = position
        currentFragment = `object` as? Fragment
    }
}