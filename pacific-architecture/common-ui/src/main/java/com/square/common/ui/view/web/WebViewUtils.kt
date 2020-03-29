@file:JvmName("WebViewUtils")

package com.square.common.ui.view.web

import android.os.Build
import android.webkit.WebView
import com.square.guava.android.verifySdk

fun enableSlowWholeDocumentDraw() {
    if (verifySdk(Build.VERSION_CODES.LOLLIPOP)) {
        WebView.enableSlowWholeDocumentDraw()
    }
}

fun enabledWebContentsDebugging() {
    if (verifySdk(Build.VERSION_CODES.KITKAT)) {
        WebView.setWebContentsDebuggingEnabled(true)
    }
}