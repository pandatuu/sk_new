package com.example.sk_android.mvp.view.activity.myhelpfeedback

import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface HelpFeedbackApi {
    // https://help.sk.cgland.top/api/helps
    @Headers("Content-Type: application/json")
    @GET("/api/helps")
    abstract fun getHelpInformation(): Observable<JsonObject>

    @Headers("Content-Type: application/json")
    @GET("/api/helps/{id}/children")
    abstract fun getChildrenInformation(@Path("id") id : String): Observable<JsonObject>

}