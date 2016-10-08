package com.pacific.example.mvc.view;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pacific.example.R;
import com.pacific.example.mvc.controller.ExploreFragment;
import com.pacific.example.decoration.HorizontalItemDecoration;
import com.pacific.mvc.FragmentView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExploreView extends FragmentView<ExploreFragment> {

    @BindView(R.id.rv_explore)
    RecyclerView recyclerView;
    @BindView(R.id.wrl_explore)
    SwipeRefreshLayout swipeRefreshLayout;

    public ExploreView(ExploreFragment fragment) {
        super(fragment);
    }

    @Override
    protected void initialize(Object... args) {
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
        recyclerView.setAdapter((RecyclerView.Adapter) args[0]);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_dark, R.color.holo_red, R.color.holo_green);
        recyclerView.setLayoutManager(new LinearLayoutManager(context(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new HorizontalItemDecoration
                .Builder(context())
                .colorResId(R.color.divider)
                .sizeResId(R.dimen.height_explore_divider)
                .showLastDivider()
                .build());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fragment.fetchNavigationExtra();
            }
        });
        if (fragment.isNew()) {
            fragment.fetchNavigationExtra();
        }
    }

    public void setRefreshing(final boolean refreshing) {
        swipeRefreshLayout.setRefreshing(refreshing);
    }

    public void setViewVisible(boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public void clickSnack(int position) {
        Snackbar.make(recyclerView, "click item " + String.valueOf(position), Snackbar.LENGTH_SHORT)
                .setAction(R.string.close, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }
}
