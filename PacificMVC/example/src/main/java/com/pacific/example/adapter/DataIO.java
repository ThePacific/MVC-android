package com.pacific.example.adapter;

import java.util.ArrayList;
import java.util.List;

interface DataIO<T> {

    public void add(T elem);

    public void addAll(List<T> elem);

    public void remove(T elem);

    public void remove(List<T> list);

    public void removeAt(int index);

    public void clear();

    public void replace(T oldElem, T newElem);

    public void replaceAt(int index, T elem);

    public void replaceAll(List<T> elem);

    public ArrayList<T> getAll();

    public T get(int position);

    public int getSize();

    public boolean contains(T elem);
}
