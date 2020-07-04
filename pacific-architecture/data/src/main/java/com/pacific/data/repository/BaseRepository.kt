package com.pacific.data.repository

import com.pacific.data.db.AppDatabase
import com.pacific.data.files.AppPrefsManager
import com.pacific.data.http.service.DataService

abstract class BaseRepository(
        protected val dataService: DataService,
        protected val appDatabase: AppDatabase,
        protected val appPrefsManager: AppPrefsManager
)