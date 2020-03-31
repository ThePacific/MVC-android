package com.pacific.core.mvvm

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.pacific.core.R

abstract class BaseFragment(
    @LayoutRes contentLayoutId: Int = 0
) : Fragment(contentLayoutId) {

    private var postponedTransition = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        TransitionInflater.from(context).run {
            enterTransition = inflateTransition(R.transition.fragment_enter)
            exitTransition = inflateTransition(R.transition.fragment_exit)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.isClickable = true
    }

    override fun postponeEnterTransition() {
        super.postponeEnterTransition()
        postponedTransition = true
    }

    override fun onStart() {
        super.onStart()
        if (postponedTransition) {
            // If we're postponedTransition and haven't started a transition yet,
            // we'll delay for a max of 5 seconds
            view?.postDelayed(::scheduleStartPostponedTransitions, 5000)
        }
    }

    override fun onStop() {
        super.onStop()
        postponedTransition = false
    }

    override fun startPostponedEnterTransition() {
        postponedTransition = false
        super.startPostponedEnterTransition()
    }

    open fun scheduleStartPostponedTransitions() {
        if (postponedTransition) {
            view?.doOnLayout {
                (it.parent as? ViewGroup)?.doOnPreDraw {
                    startPostponedEnterTransition()
                }
            }
            postponedTransition = false
        }
        val activity = requireActivity()
        if (activity is BaseActivity) {
            activity.scheduleStartPostponedTransitions()
        }
    }

    open fun onBackPressed(): Boolean = false
}