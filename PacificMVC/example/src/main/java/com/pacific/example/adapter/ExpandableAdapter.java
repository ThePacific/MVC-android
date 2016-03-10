package com.pacific.example.adapter;

import android.content.Context;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpandableAdapter<T, H> extends BaseExpandableListAdapter {

    protected List<T> elements;
    protected Context context;
    protected int elementIndex = -1;
    protected int childIndex = -1;

    public ExpandableAdapter(Context context) {
        elements = new ArrayList<>();
        this.context = context;
    }

    public void replaceAll(List<T> elements) {
        if (getGroupCount() > 0) {
            this.elements.clear();
        }
        this.elements.addAll(elements);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elements) {
        this.elements.addAll(elements);
        notifyDataSetChanged();
    }

    public void addAll(int location, List<T> elements) {
        this.elements.addAll(location, elements);
        notifyDataSetChanged();
    }

    public void add(T element) {
        this.elements.add(element);
        notifyDataSetChanged();
    }

    public void add(int location, T element) {
        this.elements.add(location, element);
        notifyDataSetChanged();
    }

    public void remove(T element) {
        this.elements.remove(element);
        notifyDataSetChanged();
    }

    public void remove(int location) {
        this.elements.remove(location);
        notifyDataSetChanged();
    }

    public void remove(List<T> elements) {
        this.elements.remove(elements);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return elements.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition < getGroupCount()) {
            List<H> children = getChildren(groupPosition);
            if (children != null) {
                return children.size();
            } else {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public T getGroup(int groupPosition) {
        return elements.get(groupPosition);
    }

    @Override
    public H getChild(int groupPosition, int childPosition) {
        if (groupPosition < getGroupCount() && childPosition < getChildrenCount(groupPosition)) {
            return getChildren(groupPosition).get(childPosition);
        }
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (groupPosition < getGroupCount()) {
            return childPosition;
        }
        return -1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public int getGroupIndex() {
        return elementIndex;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public void setGroupIndex(int elementIndex) {
        this.elementIndex = elementIndex;
    }

    public void setChildIndex(int childIndex) {
        this.childIndex = childIndex;
    }

    protected abstract List<H> getChildren(int groupPosition);
}
