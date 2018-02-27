package com.pacific.example.model

import com.pacific.adapter.SimpleRecyclerItem
import com.pacific.adapter.ViewHolder
import com.pacific.arch.example.R
import com.pacific.arch.example.databinding.ItemHomeApkBinding

class Apk(val iconUrl: String) : SimpleRecyclerItem() {

    override fun getLayout() = R.layout.item_home_apk

    override fun bind(holder: ViewHolder?) {
        val binding: ItemHomeApkBinding = holder?.binding()!!
        holder.attachImageLoader(R.id.img_icon)
    }
}