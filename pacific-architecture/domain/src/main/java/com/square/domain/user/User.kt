package com.square.domain.user

data class User(
    val userId: Long,
    val loginName: String,
    val loginPassword: String,
    val firstName: String,
    val lastName: String
)