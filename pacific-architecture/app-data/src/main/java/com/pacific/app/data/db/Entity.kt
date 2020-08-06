package com.pacific.app.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user"
)
data class DbUser(

    @ColumnInfo(name = "_id") @PrimaryKey(autoGenerate = true) val _id: Long,

    @ColumnInfo(name = "loginName") val loginName: String,

    @ColumnInfo(name = "loginPassword") val loginPassword: String,

    @ColumnInfo(name = "isSelected") val isSelected: Boolean
)