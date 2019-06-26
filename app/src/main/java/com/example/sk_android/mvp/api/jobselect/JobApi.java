package com.example.sk_android.mvp.api.jobselect;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.*;


public interface JobApi {


    //获取用户全部搜藏
    //http://organization-position.sk.cgland.top
    //@Headers("Content-Type: application/json")
    @GET("/api/v1/favorites/")
    Observable<JsonObject> getFavorites(
            @Query("_page") Integer _page,
            @Query("_limit") Integer _limit,
            @Query("type") String type
    );

    @POST("/api/v1/favorites/")
    Observable<Response<String>> addFavorite(
            @Body RequestBody array
    );

    @DELETE("/api/v1/favorites/{id}")
    Observable<Response<String>> unlikeFavorite(
            @Path("id") String id
    );


    @GET("/api/industries")
    Observable<JsonArray> getAllIndustries(
            @Query("_top") Boolean _top
    );






}
