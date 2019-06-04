package com.example.sk_android.mvp.api.message;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChatApi {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/storage")
    Observable<String> sendImage(@Body RequestBody array);


}
