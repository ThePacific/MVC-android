package com.pacific.example

import android.arch.lifecycle.Lifecycle
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pacific.arch.data.Status
import com.pacific.arch.example.MainActivity
import com.pacific.arch.example.R
import com.pacific.arch.example.databinding.AppsBottomSheetBinding
import com.pacific.arch.presentation.BottomSheetDialogFragment
import com.pacific.arch.presentation.ViewModelSource
import com.pacific.arch.presentation.dialogFragmentViewModel
import com.pacific.arch.presentation.fragmentDataBinding
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.kotlin.autoDisposable
import timber.log.Timber
import javax.inject.Inject

class AppsBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {

    private val model: MainFragmentViewModel by dialogFragmentViewModel(MainFragmentViewModel::class.java)
    private lateinit var binding: AppsBottomSheetBinding

    @Inject
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.e(model.toString())
        Timber.e(mainActivity.toString())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = inflater.fragmentDataBinding(R.layout.apps_bottom_sheet, container)
        binding.btnOk.setOnClickListener(this)
        return binding.root
    }

    override fun viewModelSource() = ViewModelSource.PARENT_FRAGMENT

    override fun onClick(v: View?) {
        model.requestUser()
                .autoDisposable(scope(Lifecycle.Event.ON_STOP))
                .subscribe {
                    when (it.status) {
                        Status.IN_PROGRESS -> {
                            Timber.e("state is %s", "loading......")
                        }
                        Status.ERROR -> {
                            Timber.e("state is %s", "error......")
                        }
                        Status.SUCCESS -> {
                            Timber.e("state is %s", "success......")

                        }
                        Status.IRRELEVANT -> {
                            Timber.e("state is %s", "irrelevant.....")
                        }
                    }
                }
    }

    companion object {
        fun newInstance() = AppsBottomSheet()
    }
}