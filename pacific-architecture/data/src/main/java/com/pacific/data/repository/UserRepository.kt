package com.pacific.data.repository

import com.pacific.data.base.AppContext
import com.pacific.data.db.AppDatabase
import com.pacific.data.file.AppPrefsManager
import com.pacific.data.http.service.DataService
import com.pacific.guava.Guava
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