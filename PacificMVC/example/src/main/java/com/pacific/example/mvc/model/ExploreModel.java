package com.pacific.example.mvc.model;

import android.view.View;

import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.pacific.example.bean.ExploreBean;
import com.pacific.example.mvc.view.ExploreView;
import com.pacific.example.R;
import com.pacific.mvc.FragmentMVCModel;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ExploreModel extends FragmentMVCModel<ExploreView> {

    private RecyclerAdapter<ExploreBean> adapter;

    public ExploreModel(ExploreView fragment) {
        super(fragment);
        adapter = new RecyclerAdapter<ExploreBean>(mvcView.getContext(), R.layout.item_explore) {
            @Override
            protected void convert(final RecyclerAdapterHelper helper, ExploreBean exploreBean) {
                helper.setImageResource(R.id.img_explore_icon, exploreBean.getIconResId());
                helper.setText(R.id.tv_explore_name, exploreBean.getName());
                helper.setText(R.id.tv_explore_desc, exploreBean.getDescription());
                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mvcView.clickSnack(helper.getAdapterPosition());
                    }
                });
            }
        };
    }

    public RecyclerAdapter<ExploreBean> getAdapter() {
        return adapter;
    }

    public void setRefreshing(final boolean refreshing) {
        mvcView.setRefreshing(refreshing);
    }

    public void setViewVisible(boolean visible) {
        mvcView.setViewVisible(visible);
    }

    public void fetchNavigationExtra() {
        Observable
                .just(12)
                .map(new Func1<Integer, List<ExploreBean>>() {
                    @Override
                    public List<ExploreBean> call(Integer integer) {
                        if (adapter.getItemCount() > integer) return null;
                        List<ExploreBean> list = new ArrayList<>();
                        list.add(new ExploreBean(R.drawable.web, "web work", "start：2016.01.01，end: 2016.02.01"));
                        list.add(new ExploreBean(R.drawable.smart_ticket, "PC work", "start：2016.01.01，end: 2016.02.01"));
                        return list;
                    }
                })
                .compose(mvcView.getController().<List<ExploreBean>>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ExploreBean>>() {
                    @Override
                    public void call(List<ExploreBean> list) {
                        if (list != null) {
                            adapter.addAll(list);
                        }
                        setRefreshing(false);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throw new RuntimeException(throwable.toString());
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        setRefreshing(false);
                    }
                });
    }
}
