package com.pacific.app.architecture.data.repository

import com.pacific.app.architecture.data.base.AppContext
import com.pacific.app.architecture.data.db.AppDatabase
import com.pacific.app.architecture.data.file.AppPrefsManager
import com.pacific.app.architecture.data.http.service.DataService
import com.pacific.guava.jvm.Guava
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    dataService: DataService,
    appContext: AppContext,
    appDatabase: AppDatabase,
    appPrefsManager: AppPrefsManager
) : BaseRepository(dataService, appContext, appDatabase, appPrefsManager) {

    fun login() {
        require(!isLogin())
        Guava.timber.d("", userId.toString())
        Guava.timber.d("", token1)
    }

    fun register() {
    }

    fun logout() {
        appPrefsManager.setUserId(0L)
        appPrefsManager.setToken1("")
    }

    fun user() {
    }
}