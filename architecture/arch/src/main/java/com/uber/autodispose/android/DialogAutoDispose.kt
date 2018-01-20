@file:Suppress("NOTHING_TO_INLINE")

package com.uber.autodispose.android

import android.app.Dialog
import com.uber.autodispose.LifecycleScopeProvider
import io.reactivex.annotations.CheckReturnValue

@CheckReturnValue
inline fun Dialog.scope(): LifecycleScopeProvider<*> = DialogScopeProvider.from(this)