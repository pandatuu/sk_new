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


}
