package com.square.domain.game

data class Status(
    val issue: String,
    val time: Long,
    val blockTime: Long,
    val previewIssue: String,
    val previewNumbers: String,
    val status: Int
)