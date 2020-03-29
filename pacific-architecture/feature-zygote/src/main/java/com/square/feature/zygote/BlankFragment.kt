package com.square.feature.zygote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.square.common.ui.adapter.RecyclerAdapter

class BlankFragment : Fragment() {

    private val adapterTableLeftHeader = RecyclerAdapter()
    private val adapterTableTopHeader = RecyclerAdapter()
    private val adapterTableRow = RecyclerAdapter()
    private val adapterTable = RecyclerAdapter()
    private val table = Table(adapterTableRow, adapterTableTopHeader, this)
    private var contentView: View? = null

    private val recyclerHeaderItemTouchListener = object : RecyclerView.OnItemTouchListener {

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            table.recyclerRow?.dispatchTouchEvent(e)
        }

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            return true
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }

    private val syncOnScrollListener = object : RecyclerView.OnScrollListener() {

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            contentView?.findViewById<RecyclerView>(R.id.recycler_left_header)?.scrollBy(0, dy)
        }
    }

    private val goodScrollItemTouchListener = object : RecyclerView.OnItemTouchListener {

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            if (
                e.action == MotionEvent.ACTION_DOWN &&
                rv.scrollState == RecyclerView.SCROLL_STATE_SETTLING
            ) {
                rv.stopScroll()
            }
            return false
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_blank, container, false)
            contentView!!.findViewById<RecyclerView>(R.id.recycler_left_header).apply {
                addOnItemTouchListener(recyclerHeaderItemTouchListener)
                layoutManager = LinearLayoutManager(
                    activity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = adapterTableLeftHeader
            }
            contentView!!.findViewById<RecyclerView>(R.id.recycler_table).apply {
                addOnItemTouchListener(goodScrollItemTouchListener)
                layoutManager = LinearLayoutManager(
                    activity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapterTable.add(table)
                adapter = adapterTable
            }

            adapterTableTopHeader.add(Table.TopHeader())

            val leftHeaderList = ArrayList<Table.LeftHeader>()
            repeat(50) { leftHeaderList.add(Table.LeftHeader()) }
            adapterTableLeftHeader.addAll(leftHeaderList.toList())

            val rowList = ArrayList<Table.Row>()
            repeat(50) { rowList.add(Table.Row()) }
            adapterTableRow.addAll(rowList.toList())
        }
        return contentView
    }

    override fun onDestroyView() {
        super.onDestroyView()
        contentView = null
    }

    fun attachSyncRecyclerLeftHeaderScroll(recyclerRow: RecyclerView) {
        recyclerRow.addOnScrollListener(syncOnScrollListener)
    }

    companion object {

        @JvmStatic
        fun newInstance() = BlankFragment().apply {
            retainInstance = true
        }
    }
}
