package com.example.sk_android.utils

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface UpLoadApi {

    @POST("/api/v1/storage")
    fun upLoadPic(@Body array : RequestBody): Observable<JsonObject>
}