package com.pacific.app.architecture.core.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pacific.app.architecture.data.db.AppDatabase
import com.pacific.app.architecture.data.db.DbUser

@Database(
    entities = [
        DbUser::class
    ],
    version = 1,
    exportSchema = true
)
abstract class RoomAppDatabase : RoomDatabase(), AppDatabase