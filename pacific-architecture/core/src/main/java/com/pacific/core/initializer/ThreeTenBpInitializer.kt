package com.pacific.core.initializer

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.threeten.bp.zone.ZoneRulesProvider
import javax.inject.Inject

class ThreeTenBpInitializer @Inject constructor() : AppInitializer {

    override fun initialize(app: Application) {
        // Init ThreeTenABP
        AndroidThreeTen.init(app)

        // Query the ZoneRulesProvider so that it is loaded on a background coroutine
        GlobalScope.launch(Dispatchers.IO) {
            ZoneRulesProvider.getAvailableZoneIds()
        }
    }
}