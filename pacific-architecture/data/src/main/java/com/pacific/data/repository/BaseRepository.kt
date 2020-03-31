package com.pacific.data.repository

import com.pacific.data.db.AppDatabase
import com.pacific.data.http.service.ApiService
import com.pacific.data.http.service.SuspendApiService

abstract class BaseRepository(
    protected val apiService: ApiService,
    protected val suspendApiService: SuspendApiService,
    protected val db: AppDatabase
)