package com.example.sk_android.mvp.view.activity.jobselect

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface JobSelectApi {
    // https://help.sk.cgland.top/api/helps
    @Headers("Content-Type: application/json")
    @POST("/api/reports")
    fun creatReport(@Body body: RequestBody): Observable<Response<String>>

}