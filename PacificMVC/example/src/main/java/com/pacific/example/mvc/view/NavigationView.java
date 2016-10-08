package com.pacific.example.mvc.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pacific.adapter.RecyclerAdapter;
import com.pacific.example.R;
import com.pacific.example.mvc.controller.NavigationFragment;
import com.pacific.mvc.FragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationView extends FragmentView<NavigationFragment> {
    @BindView(R.id.rv_menu)
    RecyclerView recyclerView;
    private final int spanNum = 3;

    public NavigationView(NavigationFragment fragment) {
        super(fragment);
    }

    @Override
    protected void initialize(Object... args) {
        ButterKnife.bind(this, view);
        recyclerView.setAdapter((RecyclerAdapter) args[0]);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context(), spanNum);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        fragment.loadMenu();
    }

    public void setViewVisible(boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }
}
