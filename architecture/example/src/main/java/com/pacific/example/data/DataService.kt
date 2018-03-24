package com.pacific.example.data

import android.arch.lifecycle.Lifecycle
import com.pacific.example.model.EnvelopeImpl
import com.pacific.example.model.Installer
import com.pacific.example.model.User
import io.reactivex.Single
import io.reactivex.processors.PublishProcessor
import io.reactivex.subjects.PublishSubject
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

@JvmField
var APP_UI_DEEP = 0

@JvmField
val APP_LIFECYCLE: PublishSubject<Lifecycle.Event> = PublishSubject.create()

@JvmField
val SOCKET_SERVICE: PublishProcessor<String> = PublishProcessor.create()


interface WebService {
    @FormUrlEncoded
    @POST("app/user")
    fun register(@FieldMap args: Map<String, String>): Single<EnvelopeImpl<User>>

    @GET("app/version")
    fun getInstaller(@FieldMap args: Map<String, String>): Single<EnvelopeImpl<Installer>>
}