package com.pacific.arch.kotlin;

import com.squareup.moshi.Json;

import java.util.List;

public class Page<T> {
    @Json(name = "my_list")
    public final List<T> list;

    public Page(List<T> list) {
        this.list = list;
    }
}
