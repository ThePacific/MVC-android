package com.square.feature.zygote

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.square.common.ui.adapter.DefaultBinding
import com.square.common.ui.adapter.RecyclerAdapter
import com.square.common.ui.adapter.SimpleRecyclerItem
import com.square.common.ui.adapter.ViewHolder

class Table(
    private val adapterTableRow: RecyclerAdapter,
    private val adapterTableTopHeader: RecyclerAdapter,
    private var fragment: BlankFragment?
) : SimpleRecyclerItem() {

    var recyclerRow: RecyclerView? = null
        private set

    private fun requestLayout(itemView: View) {
        itemView.layoutParams.width = RecyclerView.LayoutParams.WRAP_CONTENT
        itemView.requestLayout()
    }

    override fun getLayout(): Int = R.layout.item_table

    override fun bind(holder: ViewHolder) {
        holder.itemView.findViewById<RecyclerView>(R.id.recycler_top_header).apply {
            layoutManager = LinearLayoutManager(
                fragment?.activity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = adapterTableTopHeader
        }
        holder.itemView.findViewById<RecyclerView>(R.id.recycler_row).apply {
            recyclerRow = this
            fragment?.attachSyncRecyclerLeftHeaderScroll(this)
            layoutManager = LinearLayoutManager(
                fragment?.activity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = adapterTableRow
        }
        requestLayout(holder.itemView)
        fragment = null
    }

    class LeftHeader : SimpleRecyclerItem() {

        override fun getLayout(): Int = R.layout.item_table_left_header

        override fun bind(holder: ViewHolder) {
            val binding: DefaultBinding = holder.binding()
            binding.setText(R.id.name, "left-${holder.currentPosition}")
            if (holder.currentPosition % 2 == 0) {
                holder.itemView.setBackgroundResource(R.color.colorAccent)
            } else {
                holder.itemView.setBackgroundResource(R.color.colorPrimary)
            }
        }
    }

    class TopHeader : SimpleRecyclerItem() {

        override fun getLayout(): Int = R.layout.item_table_top_header

        override fun bind(holder: ViewHolder) {
            holder.itemView.setBackgroundResource(R.color.colorPrimaryDark)
        }
    }

    class Row : SimpleRecyclerItem() {

        override fun getLayout(): Int = R.layout.item_table_row

        override fun bind(holder: ViewHolder) {
            if (holder.currentPosition % 2 == 0) {
                holder.itemView.setBackgroundResource(R.color.colorAccent)
            } else {
                holder.itemView.setBackgroundResource(R.color.colorPrimary)
            }
        }
    }
}