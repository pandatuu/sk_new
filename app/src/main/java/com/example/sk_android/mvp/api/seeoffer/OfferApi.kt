package com.example.sk_android.mvp.api.seeoffer

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface OfferApi {
    @Headers("Content-Type: application/json")
    @GET("/api/organization-offer-histories/{id}")
    fun getUserPrivacy(@Path("id") id: String): Observable<Response<JsonObject>>

    @Headers("Content-Type: application/json")
    @PUT("/api/organization-offer-histories/{id}")
    fun updateOfferState(@Path("id") id: String, @Body body: RequestBody): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @POST("/api/mails")
    fun createToMyMail(@Body body: RequestBody): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/{id}")
    fun getmail(@Path("id") id: String): Observable<Response<JsonObject>>
}