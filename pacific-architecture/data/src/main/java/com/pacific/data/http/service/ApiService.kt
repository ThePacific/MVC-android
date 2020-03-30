package com.pacific.data.http.service

import com.pacific.data.http.entities.ApiGame
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("/merchant/freetf_merchant_user/memberLogin")
    fun apiToken(@FieldMap fields: Map<String, String>): Call<String>

    @GET("/merchant/merchant_user/all_ticket")
    fun apiGame(): Call<List<ApiGame>>

    @GET("/merchant/merchant_user/features_tickets")
    fun apiNewGame(): Call<List<ApiGame>>
}