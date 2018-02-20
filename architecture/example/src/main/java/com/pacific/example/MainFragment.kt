package com.pacific.example

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pacific.arch.data.Status
import com.pacific.arch.example.R
import com.pacific.arch.example.databinding.FragmentMainBinding
import com.pacific.arch.presentation.*
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable

class MainFragment : Fragment(), View.OnClickListener {

    private val model by fragmentViewModel(MainFragmentViewModel::class.java)
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflater.dataBinding(R.layout.fragment_main, container)
        binding.btnSummit.setOnClickListener(this)
        return binding.root
    }

    override fun viewModelSource(): ViewModelSource {
        return ViewModelSource.NONE
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_summit -> {
                model.requestUser()
                        .autoDisposable(scope(Lifecycle.Event.ON_PAUSE))
                        .subscribe({
                            when (it.status) {
                                Status.IN_PROGRESS -> {
                                    Log.e("loading", it.data)
                                }
                                Status.ERROR -> {
                                    Log.e("error", it.data)
                                }
                                Status.SUCCESS -> {
                                    Log.e("success", it.data)
                                }
                                Status.IRRELEVANT -> {
                                    Log.e("none", it.data)
                                }
                            }
                        })
                exit(model.getApplication(), true)
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
