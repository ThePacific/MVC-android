package com.pacific.arch.presentation

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog

abstract class BottomSheetDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(context!!, theme)
    }
}
