package com.pacific.guava.android.ui.view.web

import android.os.Build
import android.webkit.WebView
import com.pacific.guava.android.verifySdk

/**
 * android 5+，设置WebView的DOM模式
 */
fun enableSlowWholeDocumentDraw() {
    if (verifySdk(Build.VERSION_CODES.LOLLIPOP)) {
        WebView.enableSlowWholeDocumentDraw()
    }
}

/**
 * android 5+，设置WebView调试模式
 */
fun enabledWebContentsDebugging() {
    if (verifySdk(Build.VERSION_CODES.KITKAT)) {
        WebView.setWebContentsDebuggingEnabled(true)
    }
}