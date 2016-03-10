package com.pacific.example.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraction class of a BaseAdapter in which you only need to provide the
 * convert() implementation.<br/>
 * Using the provided RecycleAdapterHelper, your code is minimalist.
 *
 * @param <T> The type of the items in the list.
 *            <p/>
 *            Deprecated, Please use BaseRecycleAdapter with RecycleView instead.
 */
public abstract class BaseQuickAdapter<T, H extends AdapterHelper> extends BaseAdapter implements DataIO<T> {

    protected final Context context;
    protected final int layoutResId;
    protected final ArrayList<T> data;

    /**
     * Create a QuickAdapter2.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     */
    public BaseQuickAdapter(Context context, int layoutResId) {
        this(context, layoutResId, null);
    }

    /**
     * Create a QuickAdapter2.
     *
     * @param context     The context.
     * @param layoutResId The layout resource id of each item.
     * @param data        A new list is created out of this one to avoid mutable list
     */
    public BaseQuickAdapter(Context context, int layoutResId, List<T> data) {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.context = context;
        this.layoutResId = layoutResId;
    }

    @Override
    public int getCount() {
        return getSize();
    }

    @Override
    public T getItem(int position) {
        return get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position >= data.size() ? 1 : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final H helper = getAdapterHelper(position, convertView, parent);
        T item = getItem(position);
        convert(helper, item);
        helper.setAssociatedObject(item);
        return helper.getItemView();
    }

    @Override
    public boolean isEnabled(int position) {
        return position < data.size();
    }

    @Override
    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(List<T> elem) {
        data.addAll(elem);
        notifyDataSetChanged();
    }

    @Override
    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    @Override
    public void removeAt(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public void remove(List<T> list) {
        data.removeAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        if (data != null && data.size() > 0) {
            data.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public void replace(T oldElem, T newElem) {
        replaceAt(data.indexOf(oldElem), newElem);
    }

    @Override
    public void replaceAt(int index, T elem) {
        data.set(index, elem);
        notifyDataSetChanged();
    }

    @Override
    public void replaceAll(List<T> elem) {
        if (data.size() > 0) {
            data.clear();
        }
        data.addAll(elem);
        notifyDataSetChanged();
    }

    @Override
    public T get(int position) {
        if (position >= data.size())
            return null;
        return data.get(position);
    }

    @Override
    public ArrayList<T> getAll() {
        return data;
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public boolean contains(T elem) {
        return data.contains(elem);
    }

    /**
     * Implement this method and use the helper to adapt the view to the given
     * item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    protected abstract void convert(H helper, T item);

    /**
     * You can override this method to use a custom RecycleAdapterHelper in order
     * to fit your needs
     *
     * @param position    The position of the item within the adapter's data set of the
     *                    item whose view we want.
     * @param convertView The old view to reuse, if possible. Note: You should check
     *                    that this view is non-null and of an appropriate type before
     *                    using. If it is not possible to convert this view to display
     *                    the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so
     *                    that this View is always of the right type (see
     *                    {@link #getViewTypeCount()} and {@link #getItemViewType(int)}
     *                    ).
     * @param parent      The parent that this view will eventually be attached to
     * @return An instance of RecycleAdapterHelper
     */
    protected abstract H getAdapterHelper(int position, View convertView, ViewGroup parent);

}
