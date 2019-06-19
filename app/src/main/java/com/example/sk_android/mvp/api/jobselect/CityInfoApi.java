package com.example.sk_android.mvp.api.jobselect;

import com.google.gson.JsonArray;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface CityInfoApi {



    //http://organization-position.sk.cgland.top
    //@Headers("Content-Type: application/json")
    @GET("/api/areas")
    Observable<JsonArray> getAllAreaInfo(
                                            @Query("_top") Boolean _top

    );


}
