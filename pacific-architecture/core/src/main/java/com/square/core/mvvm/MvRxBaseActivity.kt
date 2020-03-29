package com.square.core.mvvm

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.airbnb.mvrx.MvRxView
import com.airbnb.mvrx.MvRxViewId

abstract class MvRxBaseActivity(
    @LayoutRes contentLayoutId: Int = 0
) : BaseActivity(contentLayoutId), MvRxView {

    private val mvrxViewIdProperty = MvRxViewId()
    final override val mvrxViewId: String by mvrxViewIdProperty

    override fun onCreate(savedInstanceState: Bundle?) {
        mvrxViewIdProperty.restoreFrom(savedInstanceState)
        super.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvrxViewIdProperty.saveTo(outState)
    }

    override fun onStart() {
        super.onStart()
        // This ensures that invalidate() is called for static screens that don't
        // subscribe to a ViewModel.
        postInvalidate()
    }
}