package com.pacific.data.test

import androidx.annotation.VisibleForTesting
import com.pacific.data.dataComponent
import com.pacific.guava.Guava

@VisibleForTesting
fun setupTest() {
    Guava.isDebug = true
    dataComponent = DaggerTestComponent.factory().create()
}
