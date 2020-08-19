package com.pacific.app.architecture.data.db

interface AppDatabase {

    fun userDao(): UserDao
}