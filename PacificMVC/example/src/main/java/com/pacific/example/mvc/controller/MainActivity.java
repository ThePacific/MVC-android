package com.pacific.example.mvc.controller;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.pacific.example.R;
import com.pacific.example.mvc.model.MainModel;
import com.pacific.example.mvc.view.MainView;
import com.pacific.mvc.Activity;

public class MainActivity extends Activity<MainModel> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mvcModel = new MainModel(new MainView(this));
        /** we need to call onCreate() manually*/
        mvcModel.onCreate();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mvcModel.setNotify(menu.findItem(R.id.action_notify));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mvcModel.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        mvcModel.onUserInteraction();
    }

    @Override
    public void onBackPressed() {
        if (!mvcModel.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public MainModel.PagerAdapter getPagerAdapter() {
        return mvcModel.getPagerAdapter();
    }

    public boolean hasNotify() {
        return mvcModel.hasNotify();
    }
}
