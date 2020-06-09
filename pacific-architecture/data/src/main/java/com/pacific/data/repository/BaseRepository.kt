package com.pacific.data.repository

import com.pacific.data.db.AppDatabase
import com.pacific.data.http.service.OurApi

abstract class BaseRepository(
    protected val ourApi: OurApi,
    protected val db: AppDatabase
)