package com.pacific.data.repository

import com.pacific.data.db.AppDatabase
import com.pacific.data.files.AppPrefsManager
import com.pacific.data.http.service.DataService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
        dataService: DataService,
        appDatabase: AppDatabase,
        appPrefsManager: AppPrefsManager
) : BaseRepository(dataService, appDatabase, appPrefsManager) {

}