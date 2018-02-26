package com.pacific.arch.views.widget

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.uber.autodispose.android.internal.AutoDisposeAndroidUtil

fun EditText.setPasswordMode(isPassword: Boolean) {
    if (isPassword) {
        this.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    } else {
        this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }
    this.setSelection(this.text.toString().length)
}

fun RecyclerView.trimItemAnimator() {
    (this.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false
}

@SuppressLint("RestrictedApi")
fun View.isAttached() = AutoDisposeAndroidUtil.isAttached(this)


fun verifySDK(version: Int) = Build.VERSION.SDK_INT >= version