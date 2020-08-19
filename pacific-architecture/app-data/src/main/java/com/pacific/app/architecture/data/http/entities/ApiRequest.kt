package com.pacific.app.architecture.data.http.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiRequest<out T : Any>(
    @Json(name = "data") val data: T? = null
)