package com.example.sk_android.mvp.api.jobselect;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserApi {


    //获取指定用户信息
    //http://organization-position.sk.cgland.top
    //@Headers("Content-Type: application/json")
    @GET("/api/v1/users/{id}")
    Observable<JsonObject> getUserInfo(
            @Path("id") String id

    );



}
