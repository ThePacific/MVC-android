package com.square.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "user"
)
data class DbUser(

    @ColumnInfo(name = "userId") @PrimaryKey val userId: Long,

    @ColumnInfo(name = "loginName") val loginName: String,

    @ColumnInfo(name = "loginPassword") val loginPassword: String,

    @ColumnInfo(name = "firstName") val firstName: String,

    @ColumnInfo(name = "lastName") val lastName: String
)