package com.pacific.example.adapter;

import android.content.Context;

import java.util.List;

/**
 * Same as QuickAdapter2, but adds an "itemChanged" boolean in the
 * convert() method params, which allows you to know if you are
 * adapting the new view to the same item or not, and therefore
 * make a difference between dataSet changed / dataSet invalidated.
 * <p/>
 * Abstraction class of a BaseAdapter in which you only need
 * to provide the convert() implementation.<br/>
 * Using the provided RecycleAdapterHelper, your code is minimalist.
 *
 * @param <T> The type of the items in the list.
 *            <p/>
 *            Deprecated, Please use EnhancedAdapter2<T> with RecycleView instead.
 */

public abstract class EnhancedAdapter<T> extends QuickAdapter<T> {

    /**
     * Create a QuickAdapter2.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     */
    public EnhancedAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    /**
     * Same as QuickAdapter2#QuickAdapter2(Context,int) but with
     * some initialization data.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public EnhancedAdapter(Context context, int layoutResId, List<T> data) {
        super(context, layoutResId, data);
    }

    @Override
    protected final void convert(AdapterHelper helper, T item) {
        boolean itemChanged = helper.associatedObject == null || !helper.associatedObject.equals(item);
        helper.associatedObject = item;
        convert(helper, item, itemChanged);
    }

    /**
     * @param helper      The helper to use to adapt the view.
     * @param item        The item you should adapt the view to.
     * @param itemChanged Whether or not the helper was bound to another object before.
     */
    protected abstract void convert(AdapterHelper helper, T item, boolean itemChanged);
}
