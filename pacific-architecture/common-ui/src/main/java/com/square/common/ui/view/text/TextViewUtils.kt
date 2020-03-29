package com.square.common.ui.view.text

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import com.square.guava.android.ime.ImeUtils

fun EditText.enableSystemSoftKeyboard(enable: Boolean) {
    ImeUtils.enableSystemSoftKeyboard(this, enable)
}

fun EditText.setPasswordVisiblity(isVisible: Boolean) {
    if (isVisible) {
        this.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    } else {
        this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }
    this.moveCursorToLast()
}

fun EditText.moveCursorToLast() = this.setSelection(this.text.length)

fun EditText.disableEditable() {
    this.clearFocus()
    this.isFocusable = false
    this.isFocusableInTouchMode = false
}

fun EditText.enableEditable(requestFocus: Boolean) {
    this.isFocusableInTouchMode = true
    this.isFocusable = true
    if (requestFocus) {
        this.requestFocus()
        this.moveCursorToLast()
    }
}

fun TextView.updateCompoundDrawables(
    start: Drawable? = null,
    top: Drawable? = null,
    end: Drawable? = null,
    bottom: Drawable? = null
) {
    setCompoundDrawables(start, top, end, bottom)
}

fun TextView.updateCompoundDrawablesWithIntrinsicBounds(
    start: Drawable? = null,
    top: Drawable? = null,
    end: Drawable? = null,
    bottom: Drawable? = null
) {
    setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
}

fun TextView.updateCompoundDrawablesRelativeWithIntrinsicBounds(
    start: Drawable? = null,
    top: Drawable? = null,
    end: Drawable? = null,
    bottom: Drawable? = null
) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
}

/**
 * Add an action which will be invoked before the text changed
 */
fun TextView.doBeforeTextChanged(
    action: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit
) {
    addTextChangedListener(beforeTextChanged = action)
}

/**
 * Add an action which will be invoked when the text is changing
 */
fun TextView.doOnTextChanged(
    action: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit
) {
    addTextChangedListener(onTextChanged = action)
}

/**
 * Add an action which will be invoked after the text changed
 */
fun TextView.doAfterTextChanged(
    action: (text: Editable?) -> Unit
) {
    addTextChangedListener(afterTextChanged = action)
}

/**
 * Add a text changed listener to this TextView using the provided actions
 */
fun TextView.addTextChangedListener(
    beforeTextChanged: ((
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit)? = null,
    onTextChanged: ((
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit)? = null,
    afterTextChanged: ((text: Editable?) -> Unit)? = null
) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged?.invoke(s)
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged?.invoke(text, start, count, after)
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged?.invoke(text, start, before, count)
        }
    })
}