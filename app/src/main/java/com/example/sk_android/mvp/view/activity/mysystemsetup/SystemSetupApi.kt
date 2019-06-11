package com.example.sk_android.mvp.view.activity.mysystemsetup

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SystemSetupApi {

    @Headers("Content-Type: application/json")
    @POST("/api/users/verify-code")
    fun sendvCode(@Body array: RequestBody): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @POST("/api/users/validate-verify-code")
    fun validateCode(@Body array: RequestBody): Observable<Response<String>>


    @Headers("Content-Type: application/json")
    @PATCH("/api/users/change-phone")
    fun changePhoneNum(@Body array: RequestBody): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/settings")
    fun getUserInformation(): Observable<Response<JsonObject>>

    @Headers("Content-Type: application/json")
    @PUT("/api/v1/users/settings")
    fun updateUserInformation(@Body array: RequestBody): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @GET("api/v1/user-greetings")
    fun getGreetings(): Observable<Response<JsonObject>>
}