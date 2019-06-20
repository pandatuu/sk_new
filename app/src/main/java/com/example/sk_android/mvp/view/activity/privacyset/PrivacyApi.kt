package com.example.sk_android.mvp.view.activity.privacyset

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface PrivacyApi {
    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/settings")
    fun getUserPrivacy(): Observable<Response<JsonObject>>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/black-list")
    fun getBlackList(): Observable<Response<JsonObject>>

    @Headers("Content-Type: application/json")
    @DELETE("/api/v1/users/black-list/{id}")
    fun deleteBlackList(@Path("id") id: String): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @PUT("/api/v1/users/settings")
    fun updateUserPrivacy(@Body array : RequestBody): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @GET("/api/organizations/{organization-id}")
    fun getCompany(@Path("organization-id") id : String): Observable<Response<JsonObject>>

    @Headers("Content-Type: application/json")
    @GET("/api/organizations/")
    fun getAllCompany(): Observable<Response<JsonObject>>

    @Headers("Content-Type: application/json")
    @GET("/api/organizations/{organization-id}/addresses")
    fun getCompanyAddress(@Path("organization-id") id : String): Observable<Response<JsonObject>>
}