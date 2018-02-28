package com.pacific.example.feature.main

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.pacific.arch.example.R
import com.pacific.arch.example.databinding.ActivityMainBinding
import com.pacific.arch.presentation.activityViewModel
import com.pacific.arch.presentation.contentView
import com.pacific.arch.views.widget.OnTabSelected
import com.pacific.example.base.BaseActivity
import com.pacific.example.views.SyncActionProvider
import javax.inject.Inject

class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)
    val model by activityViewModel(MainViewModel::class.java)

    @Inject
    lateinit var mainFragmentAdapter: MainFragmentAdapter

    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        val toggle = ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        binding.navView.setNavigationItemSelectedListener(this)
        toggle.syncState()

        binding.viewPager.offscreenPageLimit = mainFragmentAdapter.count
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelected() {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.setCurrentItem(tab!!.position, false)
            }
        })
        binding.viewPager.clearOnPageChangeListeners()
        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))
        binding.viewPager.adapter = mainFragmentAdapter
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            filmToolbarMenu()
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        when (mainFragmentAdapter.currentPosition) {
            0 -> homeToolbarMenu()
            1 -> gameToolbarMenu()
            2 -> filmToolbarMenu()
            3 -> bookToolbarMenu()
            4 -> newsToolbarMenu()
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.nav_camera -> {
            }
            R.id.nav_gallery -> {
            }
            R.id.nav_slideshow -> {
            }
            R.id.nav_manage -> {
            }
            R.id.nav_share -> {
            }
            R.id.nav_send -> {
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun homeToolbarMenu() {
        menuInflater.inflate(R.menu.fragment_home_toolbar, menu)
        val syncProvider = MenuItemCompat.getActionProvider(menu.findItem(R.id.action_sync)) as SyncActionProvider

    }

    fun newsToolbarMenu() {
        menu.findItem(R.id.action_sync).isVisible = false

    }

    fun filmToolbarMenu() {
        menu.findItem(R.id.action_sync).isVisible = false
    }

    fun gameToolbarMenu() {
        menu.findItem(R.id.action_sync).isVisible = false
    }

    fun bookToolbarMenu() {
        menu.findItem(R.id.action_sync).isVisible = false
    }
}
