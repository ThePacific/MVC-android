package com.pacific.app.architecture.store.repository

import com.pacific.app.architecture.store.db.PlatformDatabase
import com.pacific.app.architecture.store.file.PlatformPrefs
import com.pacific.app.architecture.store.http.service.DataService
import com.pacific.guava.data.PlatformContext

abstract class BaseRepository(
    protected val dataService: DataService,
    protected val platformDatabase: PlatformDatabase,
    protected val platformContext: PlatformContext,
    protected val platformPrefs: PlatformPrefs
) {
}