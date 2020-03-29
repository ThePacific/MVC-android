package com.square.data.http.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiRequest<T>(
    @Json(name = "data") val data: T? = null
)