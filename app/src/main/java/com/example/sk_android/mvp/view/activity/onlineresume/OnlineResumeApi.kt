package com.example.sk_android.mvp.view.activity.onlineresume

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface OnlineResumeApi {

    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/self")
    fun getUserSelf(): Observable<Response<JsonObject>>

    @Headers("Content-Type: application/json")
    @PUT("/api/v1/users")
    fun updateUserSelf(@Body array : RequestBody): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @GET("/api/organizations")
    fun getCompanyByName(@Query("name") name: String): Observable<Response<JsonObject>>

    @Headers("Content-Type: application/json")
    @POST("/api/v1/working-histories")
    fun createJobExperience(@Query("resume-id") resumeId: String, @Body array : RequestBody): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/working-histories/{id}")
    fun getJobExperience(@Path("id") id : String): Observable<Response<JsonObject>>

    @Headers("Content-Type: application/json")
    @PUT("/api/v1/working-histories/{id}")
    fun updateJobExperience(@Path("id") id : String, @Body array : RequestBody): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @DELETE("/api/v1/working-histories/{id}")
    fun deleteJobExperience(@Path("id") id : String): Observable<Response<String>>
}