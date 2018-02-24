package com.pacific.example.data

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepo @Inject constructor(private val prefs: SharedPreferences,
                                  private val systemDatabase: SystemDatabase) {
}