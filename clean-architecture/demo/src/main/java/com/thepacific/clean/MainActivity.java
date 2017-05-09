package com.thepacific.clean;

import android.os.Bundle;
import android.util.Log;

import com.thepacific.mvp.DaggerRxActivity;

import javax.inject.Inject;

public class MainActivity extends DaggerRxActivity {

    @Inject
    MainApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("__________", String.valueOf(application));
    }
}
