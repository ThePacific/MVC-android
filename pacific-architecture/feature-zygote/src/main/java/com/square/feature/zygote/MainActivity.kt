package com.square.feature.zygote

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.square.common.ui.view.viewpager.reduceDragSensitivity
import com.square.core.feature.ACCESS_ACTIVITY_REQUEST_CODE
import com.square.core.feature.createAccessActivityIntent

class MainActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager2
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.pager)
        viewPager.offscreenPageLimit = 10
        viewPager.adapter = MyAdapter(this)
        findViewById<TabLayout>(R.id.layout_tab).apply {
            addOnTabSelectedListener(
                object : TabLayout.OnTabSelectedListener {

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        viewPager.setCurrentItem(tab!!.position, false)
                    }
                }
            )
        }
        viewPager.reduceDragSensitivity()

        this.startActivityForResult(
            this.createAccessActivityIntent(),
            ACCESS_ACTIVITY_REQUEST_CODE
        )
    }

    class MyAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {

        private val titles = arrayOf("tab1", "tab2", "tab3", "tab4", "tab5", "tab6")

        override fun getItemCount(): Int = titles.size

        override fun createFragment(position: Int): Fragment {
            return BlankFragment.newInstance()
        }
    }
}