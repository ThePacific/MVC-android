package com.pacific.example.mvc.model;

import android.view.View;

import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.pacific.example.bean.Bean;
import com.pacific.example.mvc.view.ExploreView;
import com.pacific.example.R;
import com.pacific.mvc.FragmentModel;
import com.pacific.mvc.internal.FragmentEvent;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ExploreModel extends FragmentModel<ExploreView> {

    public ExploreModel(final ExploreView view) {
        super(view);
    }

    public void setRefreshing(final boolean refreshing) {
        view.setRefreshing(refreshing);
    }

    public void setViewVisible(boolean visible) {
        view.setViewVisible(visible);
    }

    public void fetchNavigationExtra() {
        Observable
                .just(12)
                .map(new Function<Integer, List<Bean>>() {
                    @Override
                    public List<Bean> apply(Integer integer) {
                        if (adapter.getItemCount() > integer) return null;
                        List<Bean> list = new ArrayList<>();
                        list.add(new Bean(R.drawable.web, "web work", "start：2016.01.01，end: 2016.02.01"));
                        list.add(new Bean(R.drawable.smart_ticket, "PC work", "start：2016.01.01，end: 2016.02.01"));
                        return list;
                    }
                })
                .compose(view.controller().<List<Bean>>bindUntilEvent(FragmentEvent.PAUSE))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Bean>>() {
                    @Override
                    public void accept(List<Bean> list) throws Exception {
                        if (list != null) {
                            adapter.addAll(list);
                        }
                        setRefreshing(false);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throw new RuntimeException(throwable.toString());
                    }
                }, new Action() {
                    @Override
                    public void run() {
                        setRefreshing(false);
                    }
                });


    }
}
