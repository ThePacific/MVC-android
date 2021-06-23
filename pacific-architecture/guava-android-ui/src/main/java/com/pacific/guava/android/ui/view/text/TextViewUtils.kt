package com.pacific.guava.android.ui.view.text

import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import com.pacific.guava.android.context.toColor
import com.pacific.guava.android.ime.ImeUtils

/**
 * 是否禁用系统软键盘
 */
fun EditText.enableSystemSoftKeyboard(enable: Boolean) {
    ImeUtils.enableSystemSoftKeyboard(this, enable)
}

/**
 * 是否显示为密码格式
 */
fun EditText.setPasswordVisibility(isVisible: Boolean) {
    if (isVisible) {
        this.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    } else {
        this.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }
    this.moveCursorToLast()
}

/**
 * 把光标移动到最后
 */
fun EditText.moveCursorToLast() = this.setSelection(this.text.length)

/**
 * 回退删除字符
 */
fun EditText.backspace(count: Int = 1) {
    val index = this.selectionStart
    text.delete(index - count, index)
}

/**
 * 禁用编辑
 */
fun EditText.disableEditable() {
    this.clearFocus()
    this.isFocusable = false
    this.isFocusableInTouchMode = false
}

/**
 * 恢复可编辑
 */
fun EditText.enableEditable(requestFocus: Boolean) {
    this.isFocusableInTouchMode = true
    this.isFocusable = true
    if (requestFocus) {
        this.requestFocus()
        this.moveCursorToLast()
    }
}

/**
 * 更新图标
 */
fun TextView.updateCompoundDrawables(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
) {
    setCompoundDrawables(start, top, end, bottom)
}

/**
 * 更新图标
 */
fun TextView.updateCompoundDrawablesWithIntrinsicBounds(
        start: Drawable? = null,
        top: Drawable? = null,
        end: Drawable? = null,
        bottom: Drawable? = null
) {
    setCompoundDrawablesWithIntrinsicBounds(start, top, end, bottom)
}

/**
 * 更新图标
 */
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

/**
 * 设置字体颜色
 */
fun TextView.applyColorSpan(text: String, start: Int, end: Int, @ColorRes color: Int) {
    this.applyColorSpan(text, text.substring(start, end), color)
}

/**
 * 设置字体颜色
 */
fun TextView.applyColorSpan(text: String, matcher: String, @ColorRes color: Int) {
    this.text = MySpannable(text + matcher).findAndSpan(text) {
        ForegroundColorSpan(this.context.toColor(color))
    }
}

/**
 * 设置字体颜色
 */
fun TextView.applyColorSpan2(text: String, start: Int, end: Int, @ColorRes color: Int) {
    this.applyColorSpan2(text, text.substring(start, end), color)
}

/**
 * 设置字体颜色
 */
fun TextView.applyColorSpan2(text: String, matcher: String, @ColorRes color: Int) {
    this.text = MySpannable(text + matcher).findAndSpanLast(text) {
        ForegroundColorSpan(this.context.toColor(color))
    }
}

/**
 * 设置字体颜色
 */
fun TextView.applyColor(@ColorRes color: Int) {
    this.setTextColor(this.context.toColor(color))
}
