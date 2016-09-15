package com.pacific.example.mvc.view;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.pacific.example.R;
import com.pacific.example.mvc.controller.MainActivity;
import com.pacific.mvc.ActivityView;

public class MainView extends ActivityView<MainActivity> {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem notify;

    private final int NONE_BACK_VALUE = 6;
    private final int SUPPORT_BACK_VALUE = 4;
    private final int MIDDLE_BACK_VALUE = 2;
    private final int PRIMARY_BACK_VALUE = 0;
    private boolean supportBack = false;
    private int pressedBackCount = PRIMARY_BACK_VALUE;

    public MainView(MainActivity activity) {
        super(activity);
    }

    @Override
    protected void findView() {
        toolbar = retrieveView(R.id.toolbar);
        tabLayout = retrieveView(R.id.tab_layout);
        viewPager = retrieveView(R.id.view_pager);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void setAdapter(Object... adapters) {
        viewPager.setAdapter((PagerAdapter) adapters[0]);
    }

    @Override
    protected void initialize() {
        activity.setSupportActionBar(toolbar);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.action_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.action_explore);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        toolbar.setTitle(R.string.title_navigation_fragment);
                        break;
                    case 1:
                        toolbar.setTitle(R.string.title_explore_fragment);
                        break;
                    default:
                        toolbar.setTitle(R.string.title_setting_fragment);
                        break;
                }
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onClick(View v) {

    }

    public void onUserInteraction() {
        if (supportBack) {
            supportBack = false;
        }
        if (pressedBackCount < NONE_BACK_VALUE) {
            pressedBackCount++;
        }
    }

    public boolean onBackPressed() {
        if (!supportBack && pressedBackCount != SUPPORT_BACK_VALUE) {
            supportBack = true;
            pressedBackCount = MIDDLE_BACK_VALUE;
            closeSnack();
            return true;
        }
        return false;
    }

    public void closeSnack() {
        Snackbar.make(retrieveView(R.id.v_snack), activity.getString(R.string.snack_logout), Snackbar.LENGTH_SHORT)
                .setAction(R.string.close, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    public void setNotify(MenuItem notify) {
        this.notify = notify;
        if (activity.hasNotify()) {
            this.notify.getIcon().setLevel(1);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                activity.finish();
                return true;
            default:
                return false;
        }
    }
}
