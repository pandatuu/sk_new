package com.example.sk_android.mvp.view.activity.myhelpfeedback

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*

interface HelpFeedbackApi {
    // https://help.sk.cgland.top/api/helps
    @Headers("Content-Type: application/json")
    @GET("/api/helps")
    fun getHelpInformation(): Observable<JsonObject>

    @Headers("Content-Type: application/json")
    @GET("/api/helps/{id}/children")
    fun getChildrenInformation(@Path("id") id : String): Observable<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("/api/user/login")
    fun loginUser(@Body array : RequestBody): Observable<String>

    @Headers("Content-Type: application/json")
    @GET("/api/feedbacks/mine")
    fun userFeedback(): Observable<JsonObject>

    @Headers("Content-Type: application/json")
    @POST("/api/feedbacks")
    fun createFeedback(@Body array : RequestBody): Observable<String>


    @Headers("Content-Type: application/json")
    @GET("/api/feedbacks/{id}")
    fun getFeedbackById(@Path("id") id : String): Observable<JsonObject>
}