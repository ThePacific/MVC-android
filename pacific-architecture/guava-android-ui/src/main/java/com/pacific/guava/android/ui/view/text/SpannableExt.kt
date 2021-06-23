package com.pacific.guava.android.ui.view.text

import android.text.Spannable
import android.text.Spanned.SPAN_INCLUSIVE_EXCLUSIVE

/**
 * Adds [span] to the entire text.
 *
 * Ported from
 * https://github.com/android/android-ktx/blob/89ee2e1cde1e1b0226ed944b9abd55cee0f9b9d4/src/main/java/androidx/core/text/SpannableString.kt#L32
 */
inline operator fun Spannable.plusAssign(span: Any) {
    setSpan(span, 0, length, SPAN_INCLUSIVE_EXCLUSIVE)
}