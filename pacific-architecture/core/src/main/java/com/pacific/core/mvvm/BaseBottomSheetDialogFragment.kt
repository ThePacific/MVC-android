package com.pacific.core.mvvm

import android.os.Bundle
import android.view.View
import androidx.transition.TransitionInflater
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pacific.core.R

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransitionInflater.from(requireContext()).run {
            enterTransition = inflateTransition(R.transition.fragment_enter)
            exitTransition = inflateTransition(R.transition.fragment_exit)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
    }

    open fun onBackPressed(): Boolean = false
}