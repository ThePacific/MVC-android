package com.pacific.guava.android.mvvm

import android.widget.Toast
import androidx.annotation.StringRes

object Msg {

    fun toast(msg: String) {
        Toast.makeText(AndroidX.myApp, msg, Toast.LENGTH_LONG).show()
    }

    fun toast(@StringRes msg: Int) {
        Toast.makeText(AndroidX.myApp, msg, Toast.LENGTH_LONG).show()
    }
}
