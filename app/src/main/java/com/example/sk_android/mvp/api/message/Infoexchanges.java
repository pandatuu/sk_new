package com.example.sk_android.mvp.api.message;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.*;

public interface Infoexchanges {

    //创建交换信息
    @POST("/api/info-exchanges")
    Observable<String> createExchangeInfo(@Body RequestBody array);


    //修改交换信息的状态
    @PUT("/api/info-exchanges/{id}/state")
    Observable<String> updateExchangeInfoState(@Path("id") String id, @Body RequestBody array);


    //修改面试信息的状态
    @PUT("/api/interview-agendas/{id}/state")
    Observable<ResponseBody> updateInterviewState(@Path("id") String id, @Body RequestBody array);


    //得到简历我的
    @GET("/api/v1/resumes/")
    Observable<ResponseBody> getMyResume(@Query("type") String type);

    //得到文件信息
    @Headers("Content-Type: application/json")
    @POST("/api/v1/storage/{id}")
    Observable<String> getFileDetail(@Query("id") String id);



}
