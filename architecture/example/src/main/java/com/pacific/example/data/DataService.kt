package com.pacific.example.data

import com.pacific.example.model.EnvelopeImpl
import com.pacific.example.model.Installer
import com.pacific.example.model.User
import io.reactivex.Single
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface DataService {
    @FormUrlEncoded
    @POST("app/user")
    fun register(@FieldMap args: Map<String, String>): Single<EnvelopeImpl<User>>

    @GET("app/version")
    fun getInstaller(@FieldMap args: Map<String, String>): Single<EnvelopeImpl<Installer>>
}