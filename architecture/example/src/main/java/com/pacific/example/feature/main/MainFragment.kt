package com.pacific.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pacific.arch.example.MainActivity
import com.pacific.arch.example.R
import com.pacific.arch.example.databinding.FragmentMainBinding
import com.pacific.arch.presentation.*
import com.pacific.example.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject

class MainFragment : BaseFragment(), View.OnClickListener {

    private val model by fragmentViewModel(MainFragmentViewModel::class.java)
    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.e(model.toString())
        Timber.e(mainActivity.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflater.fragmentDataBinding(R.layout.fragment_main, container)
        binding.btnShowApps.setOnClickListener(this)
        return binding.root
    }

    override fun viewModelSource(): ViewModelSource {
        return ViewModelSource.NONE
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_show_apps -> {
                showDialogFragment(childFragmentManager, AppsBottomSheet.newInstance())
            }
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
