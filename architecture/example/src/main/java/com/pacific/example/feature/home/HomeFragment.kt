package com.pacific.example.feature.home

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pacific.adapter.RecyclerAdapter
import com.pacific.arch.data.Status
import com.pacific.arch.example.R
import com.pacific.arch.example.databinding.FragmentHomeBinding
import com.pacific.arch.presentation.fragmentDataBinding
import com.pacific.arch.presentation.fragmentViewModel
import com.pacific.example.di.GlideApp
import com.pacific.example.feature.main.MainFragment
import com.pacific.example.model.Apk
import com.pacific.example.views.GridDividerDecoration
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable

class HomeFragment : MainFragment() {
    private val model by fragmentViewModel(HomeViewModel::class.java)
    private val recyclerAdapter = RecyclerAdapter()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerAdapter.setImageLoader { imageView, holder ->
            GlideApp.with(this)
                    .load(holder.getItem<Apk>().iconUrl)
                    .placeholder(R.drawable.ic_launcher)
                    .fitCenter()
                    .into(imageView)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflater.fragmentDataBinding(R.layout.fragment_home, container)
        binding.recycler.layoutManager = GridLayoutManager(mainActivity, 3)
        binding.recycler.addItemDecoration(GridDividerDecoration(mainActivity))
        binding.recycler.adapter = recyclerAdapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.loadApk().autoDisposable(scope()).subscribe({
            when (it.status) {
                Status.IN_PROGRESS -> {

                }
                Status.ERROR -> {

                }
                Status.IRRELEVANT -> {

                }
                Status.SUCCESS -> {
                    recyclerAdapter.replaceAll(it.data!!)
                }
            }
        })
    }

    companion object {
        fun newInstance() = HomeFragment()
    }
}
