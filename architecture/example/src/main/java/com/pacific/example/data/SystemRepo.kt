package com.pacific.example.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SystemRepo @Inject constructor(private val sharedPreferences: SharedPreferences,
                                     private val systemDatabase: SystemDatabase) {
}