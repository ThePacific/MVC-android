package com.pacific.example.mvc.view;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.pacific.example.R;
import com.pacific.example.mvc.controller.ExploreFragment;
import com.pacific.example.decoration.HorizontalItemDecoration;
import com.pacific.mvc.FragmentMVCView;

public class ExploreView extends FragmentMVCView<ExploreFragment> {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ExploreView(ExploreFragment fragment) {
        super(fragment);
    }

    @Override
    protected void findView() {
        recyclerView = retrieveView(R.id.rv_explore);
        swipeRefreshLayout = retrieveView(R.id.wrl_explore);
    }

    @Override
    protected void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
            }
        });
    }

    @Override
    protected void setAdapter() {
        recyclerView.setAdapter(fragment.getQuickAdapter());
    }

    @Override
    protected void initialize() {
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_dark, R.color.holo_red, R.color.holo_green);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new HorizontalItemDecoration
                .Builder(getContext())
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

    @Override
    public void onClick(View v) {
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
