package com.pacific.core.initializer

import android.app.Application

interface AppInitializer {

    fun initialize(app: Application)
}