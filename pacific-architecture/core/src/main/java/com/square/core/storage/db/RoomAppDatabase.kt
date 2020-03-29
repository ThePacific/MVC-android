package com.square.core.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.square.data.db.AppDatabase
import com.square.data.db.entities.DbUser

@Database(
    entities = [
        DbUser::class
    ],
    version = 1,
    exportSchema = true
)
abstract class RoomAppDatabase : RoomDatabase(), AppDatabase