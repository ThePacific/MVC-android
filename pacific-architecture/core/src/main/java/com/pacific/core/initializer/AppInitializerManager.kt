package com.pacific.core.initializer

import android.app.Application
import javax.inject.Inject

class AppInitializerManager @Inject constructor(
    private val initializerSet: Set<@JvmSuppressWildcards AppInitializer>
) {

    fun initialize(app: Application) {
        initializerSet.forEach {
            it.initialize(app)
        }
    }
}