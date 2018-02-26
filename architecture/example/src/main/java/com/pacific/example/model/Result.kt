package com.pacific.example.model

import com.pacific.arch.data.Envelope
import com.squareup.moshi.Json

class EnvelopeImpl<out T>(@Json(name = "status") private val status: Boolean,
                          @Json(name = "code") private val code: Int,
                          @Json(name = "message") private val message: String?,
                          @Json(name = "data") private val data: T?) : Envelope<T> {
    override fun status() = status

    override fun code() = code

    override fun message() = message ?: ""

    override fun data() = data
}

data class User(@Json(name = "login_name") val loginName: String,
                @Json(name = "register_datetime") val registerDatetime: String)

data class Installer(@Json(name = "download_version") val downloadVersion: String,
                     @Json(name = "force_update") val forceUpdate: Boolean,
                     @Json(name = "download_url") val downloadUrl: String)