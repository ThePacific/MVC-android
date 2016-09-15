package com.pacific.example.mvc.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacific.example.mvc.model.ExploreModel;
import com.pacific.example.mvc.view.ExploreView;
import com.pacific.example.R;
import com.pacific.mvc.Fragment;

public class ExploreFragment extends Fragment<ExploreModel> {

    public static ExploreFragment newInstance() {
        ExploreFragment fragment = new ExploreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        model = new ExploreModel(new ExploreView(this));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        model.setViewVisible(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        model.setRefreshing(false);
        model.setViewVisible(false);
    }

    public void fetchNavigationExtra() {
        model.fetchNavigationExtra();
    }
}
