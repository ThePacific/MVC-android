package com.pacific.app.architecture.data.repository

import com.pacific.app.architecture.data.base.AppContext
import com.pacific.app.architecture.data.db.AppDatabase
import com.pacific.app.architecture.data.file.AppPrefsManager
import com.pacific.app.architecture.data.http.service.DataService

abstract class BaseRepository(
    protected val dataService: DataService,
    protected val appContext: AppContext,
    protected val appDatabase: AppDatabase,
    protected val appPrefsManager: AppPrefsManager
) {
    protected val userId: Long get() = appPrefsManager.getUserId()

    protected val token1: String get() = appPrefsManager.getToken1()

    protected fun isLogin(): Boolean = userId > 0L && token1.length > 8
}