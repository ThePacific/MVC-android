package com.pacific.example.mvc.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MenuItem;

import com.pacific.example.mvc.controller.ExploreFragment;
import com.pacific.example.mvc.controller.NavigationFragment;
import com.pacific.example.mvc.view.MainView;
import com.pacific.mvc.ActivityMVCModel;

public class MainModel extends ActivityMVCModel<MainView> {

    private PagerAdapter pagerAdapter;

    public MainModel(MainView mvcView) {
        super(mvcView);
        pagerAdapter = new PagerAdapter(mvcView.getController().getSupportFragmentManager(), 2);
    }

    public void setNotify(MenuItem notify) {
        mvcView.setNotify(notify);
    }

    public boolean hasNotify() {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return mvcView.onOptionsItemSelected(item);
    }

    public void onUserInteraction() {
        mvcView.onUserInteraction();
    }

    public boolean onBackPressed() {
        return mvcView.onBackPressed();
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
