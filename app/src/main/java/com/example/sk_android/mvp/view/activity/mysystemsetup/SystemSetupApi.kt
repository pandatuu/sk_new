package com.example.sk_android.mvp.view.activity.mysystemsetup

import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST

interface SystemSetupApi {

    @Headers("Content-Type: application/json")
    @POST("/api/users/verify-code")
    fun sendvCode(@Body array : RequestBody): Observable<Response<String>>

    @Headers("Content-Type: application/json")
    @POST("/api/users/validate-verify-code")
    fun validateCode(@Body array : RequestBody): Observable<Response<String>>


    @Headers("Content-Type: application/json")
    @PATCH("/api/users/change-phone")
    fun changePhoneNum(@Body array : RequestBody): Observable<Response<String>>
}