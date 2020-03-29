package com.square.common.glide

import android.app.Activity

const val MATISSE_ACTIVITY_REQUEST_CODE = 2019
const val MATISSE_HOME = "MATISSE"

/**
 * Matisse.obtainResult(Intent data)
 */
fun isMatisseResult(requestCode: Int, resultCode: Int): Boolean {
    return requestCode == MATISSE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK
}