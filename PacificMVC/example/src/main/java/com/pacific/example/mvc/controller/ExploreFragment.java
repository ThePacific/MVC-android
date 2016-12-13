package com.pacific.example.mvc.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pacific.adapter.RecyclerAdapter;
import com.pacific.adapter.RecyclerAdapterHelper;
import com.pacific.example.R;
import com.pacific.example.bean.Bean;
import com.pacific.example.mvc.model.ExploreModel;
import com.pacific.example.mvc.view.ExploreView;
import com.pacific.mvc.Fragment;

public class ExploreFragment extends Fragment<ExploreModel> {

    private RecyclerAdapter<Bean> adapter;

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
        adapter = new RecyclerAdapter<Bean>(getActivity(), R.layout.item_explore) {
            @Override
            protected void convert(final RecyclerAdapterHelper helper, Bean bean) {
                helper.setImageResource(R.id.img_explore_icon, bean.getIconResId());
                helper.setText(R.id.tv_explore_name, bean.getName());
                helper.setText(R.id.tv_explore_desc, bean.getDescription());
                helper.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        model().view().clickSnack(helper.getAdapterPosition());
                    }
                });
            }
        };
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
