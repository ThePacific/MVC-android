package com.pacific.example.mvc.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pacific.adapter.RecyclerAdapter;
import com.pacific.example.R;
import com.pacific.example.mvc.controller.NavigationFragment;
import com.pacific.mvc.FragmentView;

public class NavigationView extends FragmentView<NavigationFragment> {
    private RecyclerView recyclerView;
    private final int spanNum = 3;

    public NavigationView(NavigationFragment fragment) {
        super(fragment);
    }

    @Override
    protected void findView() {
        recyclerView = retrieveView(R.id.rv_menu);
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void setAdapter(Object... adapters) {
        recyclerView.setAdapter((RecyclerAdapter)adapters[0]);
    }

    @Override
    protected void initialize() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), spanNum);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fragment.loadMenu();
    }

    @Override
    public void onClick(View v) {

    }

    public void setViewVisible(boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

}
