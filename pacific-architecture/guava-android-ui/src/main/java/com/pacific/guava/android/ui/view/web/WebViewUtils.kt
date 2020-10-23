@file:JvmName("WebViewUtils")

package com.pacific.guava.android.ui.view.web

import android.os.Build
import android.webkit.WebView
import com.pacific.guava.android.verifySdk

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