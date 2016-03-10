package com.pacific.example.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * <b>Usage</b>
 * <p/>
 * return AdapterHelper.get(context, convertView, parent, R.layout.item)
 * .setText(R.id.tvName, contact.getName())
 * .setText(R.id.tvEmails, contact.getEmails().toString())
 * .setText(R.id.tvNumbers, contact.getNumbers().toString())
 * .getView();
 * <p/>
 */

final public class AdapterHelper extends BaseAdapterHelper<AdapterHelper> {

    protected View convertView;

    private AdapterHelper(Context context, ViewGroup parent, int layoutId, int position) {
        this.position = position;
        this.views = new SparseArray();
        this.convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.convertView.setTag(this);
    }

    /**
     * This method is the only entry point to get a AdapterHelper.
     *
     * @param context     The current context.
     * @param convertView The convertView arg passed to the getView() method.
     * @param parent      The parent arg passed to the getView() method.
     * @param layoutId    The item view layout resource.
     * @param position    The adapter position.
     * @return A AdapterHelper instance.
     */
    static AdapterHelper get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new AdapterHelper(context, parent, layoutId, position);
        }

        AdapterHelper existingHelper = (AdapterHelper) convertView.getTag();
        existingHelper.position = position;
        return existingHelper;
    }

    @Override
    public View getItemView() {
        return convertView;
    }
}
