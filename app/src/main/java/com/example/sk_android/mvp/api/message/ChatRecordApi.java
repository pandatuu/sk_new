package com.example.sk_android.mvp.api.message;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChatRecordApi {

    @Headers("Content-Type: application/json")
    @POST("/api/users/verify-code")
    Observable<String> getVerfiction(@Body RequestBody array);


    @Headers("Content-Type: application/json")
    @POST("/api/users/login")
    Observable<String> userLogin(@Body RequestBody array);
}
