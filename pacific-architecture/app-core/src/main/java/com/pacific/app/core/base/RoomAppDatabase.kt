package com.pacific.app.core.base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pacific.app.data.db.AppDatabase
import com.pacific.app.data.db.DbUser

@Database(
    entities = [
        DbUser::class
    ],
    version = 1,
    exportSchema = true
)
abstract class RoomAppDatabase : RoomDatabase(), AppDatabase