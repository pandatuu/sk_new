package com.example.sk_android.mvp.api.jobselect;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface CityInfoApi {


    //获取所有的地区信息
    //http://organization-position.sk.cgland.top
    //@Headers("Content-Type: application/json")
    @GET("/api/areas")
    Observable<JsonArray> getAllAreaInfo(
                                            @Query("_top") Boolean _top

    );




    //根据Id查询地址的详细信息
    @GET("/api/areas/{id}")
    Observable<JsonObject> getAreaInfo(
            @Path("id") String id
    );


}
