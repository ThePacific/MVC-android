package com.pacific.app.architecture.core.router

import android.app.Activity
import androidx.activity.result.ActivityResult

val ActivityResult.isResultOk get() = this.resultCode == Activity.RESULT_OK