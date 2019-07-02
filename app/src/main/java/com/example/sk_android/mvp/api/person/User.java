package com.example.sk_android.mvp.api.person;

import com.google.gson.JsonObject;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface User {




    @Headers("Content-Type: application/json")
    @GET("/api/v1/users/self")
    Observable<JsonObject> getSelfInfo();



}
