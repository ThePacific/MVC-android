package com.pacific.example.mvc.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;

import com.pacific.example.mvc.controller.ExploreFragment;
import com.pacific.example.mvc.controller.NavigationFragment;
import com.pacific.example.mvc.view.MainView;
import com.pacific.mvc.ActivityModel;

public class MainModel extends ActivityModel<MainView> {

    private PagerAdapter pagerAdapter;

    public MainModel(MainView mvcView) {
        super(mvcView);
        pagerAdapter = new PagerAdapter(view.getController().getSupportFragmentManager(), 2);
    }

    public void setNotify(MenuItem notify) {
        view.setNotify(notify);
    }

    public boolean hasNotify() {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return view.onOptionsItemSelected(item);
    }

    public void onUserInteraction() {
        view.onUserInteraction();
    }

    public boolean onBackPressed() {
        return view.onBackPressed();
    }

    public PagerAdapter getPagerAdapter() {
        return pagerAdapter;
    }

    public static class PagerAdapter extends FragmentPagerAdapter {
        private int count;

        public PagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.count = count;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NavigationFragment.newInstance();
                default:
                    return ExploreFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return count;
        }
    }
}
