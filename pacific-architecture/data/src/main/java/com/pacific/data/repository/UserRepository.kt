package com.pacific.data.repository

import com.pacific.data.db.AppDatabase
import com.pacific.data.http.service.ApiService
import com.pacific.data.http.service.SuspendApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    apiService: ApiService,
    suspendApiService: SuspendApiService,
    db: AppDatabase
) : BaseRepository(apiService, suspendApiService, db) {

}