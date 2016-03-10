package com.pacific.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;

import java.util.List;

public abstract class QuickAdapter2<T> extends BaseQuickAdapter2<T, AdapterHelper2> {

    public QuickAdapter2(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public QuickAdapter2(Context context, int layoutResId, List<T> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected AdapterHelper2 getAdapterHelper(ViewHolder viewHolder, int position) {
        return AdapterHelper2.get(viewHolder, position);
    }
}
