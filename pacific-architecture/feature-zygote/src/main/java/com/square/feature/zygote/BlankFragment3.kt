package com.square.feature.zygote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.square.common.ui.adapter.RecyclerAdapter

class BlankFragment3 : Fragment() {

    private val adapterTableRow = RecyclerAdapter()
    private var contentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (contentView == null) {
            contentView = inflater.inflate(R.layout.fragment_blank2, container, false)
            contentView!!.findViewById<RecyclerView>(R.id.recycler_table).apply {
                layoutManager = LinearLayoutManager(
                    activity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                adapter = adapterTableRow
            }

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

    companion object {

        @JvmStatic
        fun newInstance() = BlankFragment3().apply {
            retainInstance = true
        }
    }
}
