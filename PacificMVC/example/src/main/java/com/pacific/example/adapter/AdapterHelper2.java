package com.pacific.example.adapter;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;

final public class AdapterHelper2 extends BaseAdapterHelper<AdapterHelper2> {

    protected ViewHolder viewHolder;

    private AdapterHelper2(ViewHolder viewHolder, int position) {
        this.position = position;
        this.viewHolder = viewHolder;
        this.views = new SparseArray<>();
    }

    /**
     * This method is the only entry point to get a AdapterHelper.
     *
     * @param viewHolder The viewHolder arg passed to the onBindViewHolder() method.
     * @param position   The adapter position.
     * @return A AdapterHelper2 instance.
     */
    static AdapterHelper2 get(ViewHolder viewHolder, int position) {
        AdapterHelper2 helper;
        if (viewHolder.itemView.getTag() == null) {
            helper = new AdapterHelper2(viewHolder, position);
            viewHolder.itemView.setTag(helper);
        } else {
            helper = (AdapterHelper2) viewHolder.itemView.getTag();
        }
        return helper;
    }

    @Override
    public View getItemView() {
        return viewHolder.itemView;
    }

    public int getItemViewType() {
        return viewHolder.getItemViewType();
    }

    public int getAdapterPosition() {
        return viewHolder.getAdapterPosition();
    }

    public int getLayoutPosition() {
        return viewHolder.getLayoutPosition();
    }

    public int getOldPosition() {
        return viewHolder.getOldPosition();
    }

    public boolean isRecyclable() {
        return viewHolder.isRecyclable();
    }

    public void setIsRecyclable(boolean recyclable) {
        viewHolder.setIsRecyclable(recyclable);
    }
}
