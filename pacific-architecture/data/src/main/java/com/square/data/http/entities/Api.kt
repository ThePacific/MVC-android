package com.square.data.http.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiToken(
    @Json(name = "token") val token: String
) {
    @JsonClass(generateAdapter = true)
    data class Req(
        @Json(name = "member") val loginName: String,
        @Json(name = "merchant") val backLoginName: String,
        @Json(name = "password") val loginPassword: String,
        @Json(name = "loginType") val platform: Int
    )
}

@JsonClass(generateAdapter = true)
data class ApiGame(
    @Json(name = "ticketId") val id: Long,
    @Json(name = "ticketName") val name: String
)