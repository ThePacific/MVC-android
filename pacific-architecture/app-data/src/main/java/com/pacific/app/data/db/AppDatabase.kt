package com.pacific.app.data.db

interface AppDatabase {

    fun userDao(): UserDao
}