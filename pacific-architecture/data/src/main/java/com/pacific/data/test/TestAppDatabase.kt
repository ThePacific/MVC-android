package com.pacific.data.test

import androidx.annotation.VisibleForTesting
import com.pacific.data.db.AppDatabase
import com.pacific.data.db.UserDao

@VisibleForTesting
object TestAppDatabase : AppDatabase {
    
    override fun userDao(): UserDao {
        TODO("Not yet implemented")
    }
}