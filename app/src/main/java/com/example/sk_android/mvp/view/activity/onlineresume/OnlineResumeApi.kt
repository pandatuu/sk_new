package com.example.sk_android.mvp.view.activity.onlineresume

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT

interface OnlineResumeApi {

    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/self")
    fun getUserSelf(): Observable<Response<JsonObject>>

    @Headers("Content-Type: application/json")
    @PUT("/api/v1/users")
    fun updateUserSelf(@Body array : RequestBody): Observable<Response<String>>
}