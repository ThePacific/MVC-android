package com.square.common.glide

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Resources
import androidx.fragment.app.Fragment
import com.square.guava.domain.CoreJdk
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy

fun Activity.startMatisseActivity() = startMatisse(Matisse.from(this), resources)

fun Fragment.startMatisseActivity() = startMatisse(Matisse.from(this), resources)

private fun startMatisse(matisse: Matisse, resources: Resources) {
    matisse.choose(MimeType.ofImage(), true)
        .countable(false)
        .maxSelectable(1)
        .gridExpectedSize(resources.getDimensionPixelSize(R.dimen.matisse_grid_expected_size))
        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
        .thumbnailScale(0.85f)
        .imageEngine(Glide4Engine.getInstance())
        .capture(true)
        .captureStrategy(
            CaptureStrategy(
                true,
                "${CoreJdk.appProcessName}.file_provider",
                MATISSE_HOME
            )
        )
        .theme(com.zhihu.matisse.R.style.Matisse_Zhihu)
        .forResult(MATISSE_ACTIVITY_REQUEST_CODE)
}