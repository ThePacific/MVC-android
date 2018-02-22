package com.pacific.example.common

import android.support.annotation.StringRes
import android.widget.Toast
import com.pacific.arch.example.App
import com.pacific.arch.presentation.dismiss

var toast: Toast? = null

fun showToast(@StringRes stringRes: Int) {
    showToast(App.INSTANCE!!.getString(stringRes))
}

fun showToast(text: CharSequence) {
    dismiss(toast)
    toast = Toast.makeText(App.INSTANCE, text, Toast.LENGTH_SHORT)
    toast?.show()
}
