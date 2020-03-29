package com.square.data.repository

import com.square.data.db.AppDatabase
import com.square.data.http.service.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDatabase
) {

}