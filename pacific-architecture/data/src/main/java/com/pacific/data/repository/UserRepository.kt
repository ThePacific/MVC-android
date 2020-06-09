package com.pacific.data.repository

import com.pacific.data.db.AppDatabase
import com.pacific.data.http.service.OurApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    ourApi: OurApi,
    db: AppDatabase
) : BaseRepository(ourApi, db) {

}