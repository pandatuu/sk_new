package com.example.sk_android.mvp.view.activity.onlineresume

import com.google.gson.JsonObject
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*
import java.util.*

interface OnlineResumeApi {

    //获取用户基本信息
    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/self")
    fun getUserSelf(): Observable<Response<JsonObject>>

    //更新用户基本信息
    @Headers("Content-Type: application/json")
    @PUT("/api/v1/users")
    fun updateUserSelf(@Body array : RequestBody): Observable<Response<String>>

    //获取公司名
    @Headers("Content-Type: application/json")
    @GET("/api/organizations")
    fun getCompanyByName(@Query("name") name: String): Observable<Response<JsonObject>>

    //获取学校名
    @Headers("Content-Type: application/json")
    @GET("/api/organizations")
    fun getSchoolByName(@Query("name") name: String): Observable<Response<JsonObject>>

    //创建工作经历
    @Headers("Content-Type: application/json")
    @POST("/api/v1/working-histories")
    fun createJobExperience(@Query("resume-id") resumeId: String, @Body array : RequestBody): Observable<Response<String>>

    //获取工作经历
    @Headers("Content-Type: application/json")
    @GET("/api/v1/working-histories/{id}")
    fun getJobExperience(@Path("id") id : String): Observable<Response<JsonObject>>

    //更新工作经历
    @Headers("Content-Type: application/json")
    @PUT("/api/v1/working-histories/{id}")
    fun updateJobExperience(@Path("id") id : String, @Body array : RequestBody): Observable<Response<String>>

    //删除工作经历
    @Headers("Content-Type: application/json")
    @DELETE("/api/v1/working-histories/{id}")
    fun deleteJobExperience(@Path("id") id : String): Observable<Response<String>>

    //创建项目经历
    @Headers("Content-Type: application/json")
    @POST("/api/v1/project-histories")
    fun createProjectExperience(@Query("resume-id") resumeId: String, @Body array : RequestBody): Observable<Response<String>>

    //获取项目经历
    @Headers("Content-Type: application/json")
    @GET("/api/v1/project-histories/{id}")
    fun getProjectExperience(@Path("id") id : String): Observable<Response<JsonObject>>

    //更新项目经历
    @Headers("Content-Type: application/json")
    @PUT("/api/v1/project-histories/{id}")
    fun updateProjectExperience(@Path("id") id : String, @Body array : RequestBody): Observable<Response<String>>

    //删除项目经历
    @Headers("Content-Type: application/json")
    @DELETE("/api/v1/project-histories/{id}")
    fun deleteProjectExperience(@Path("id") id : String): Observable<Response<String>>

    //创建教育经历
    @Headers("Content-Type: application/json")
    @POST("/api/v1/education-histories")
    fun createEduExperience(@Query("resume-id") resumeId: String, @Body array : RequestBody): Observable<Response<String>>

    //获取教育经历
    @Headers("Content-Type: application/json")
    @GET("/api/v1/education-histories/{id}")
    fun getEduExperience(@Path("id") id : String): Observable<Response<JsonObject>>

    //更新教育经历
    @Headers("Content-Type: application/json")
    @PUT("/api/v1/education-histories/{id}")
    fun updateEduExperience(@Path("id") id : String, @Body array : RequestBody): Observable<Response<String>>

    //删除教育经历
    @Headers("Content-Type: application/json")
    @DELETE("/api/v1/education-histories/{id}")
    fun deleteEduExperience(@Path("id") id : String): Observable<Response<String>>
}