package com.square.feature.base

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class LegacyTextView : AppCompatTextView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
}