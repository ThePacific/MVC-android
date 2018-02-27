package com.pacific.example.feature.main

import android.support.v4.app.Fragment
import com.pacific.arch.example.R
import com.pacific.example.feature.book.BookFragment
import com.pacific.example.feature.film.FilmFragment
import com.pacific.example.feature.game.GameFragment
import com.pacific.example.feature.home.HomeFragment
import com.pacific.example.feature.news.NewsFragment
import com.pacific.example.views.FragmentPagerAdapter2
import javax.inject.Inject

class MainFragmentAdapter @Inject constructor(mainActivity: MainActivity)
    : FragmentPagerAdapter2(mainActivity.supportFragmentManager) {

    private val titles: Array<String> = mainActivity.resources.getStringArray(R.array.main_tab_titles)

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment.newInstance()
            1 -> GameFragment.newInstance()
            2 -> FilmFragment.newInstance()
            3 -> BookFragment.newInstance()
            4 -> NewsFragment.newInstance()
            else -> throw UnsupportedOperationException("Illegal position")
        }
    }

    override fun getPageTitle(position: Int) = titles[position]

    override fun getCount() = 5
}