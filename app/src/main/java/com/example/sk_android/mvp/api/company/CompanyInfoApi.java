package com.example.sk_android.mvp.api.company;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface CompanyInfoApi {



    //http://organization-position.sk.cgland.top
    //@Headers("Content-Type: application/json")
    @GET("/api/organizations")
    Observable<JsonObject> getCompanyInfoList(
                                            @Query("_page") Integer _page,//当前页数
                                            @Query("_limit") Integer _limit,//每页记录数
                                            @Query("name") String name,//公司名称-支持模糊
                                            @Query("acronym") String acronym,//公司简称
                                            @Query("size") String size,//公司规模
                                            @Query("financing-stage") String financingStage,//融资状况
                                            @Query("type") String type,//招聘类型
                                            @Query("coordinate") String coordinate,//坐标
                                            @Query("radius") Number radius//半径

    );


    @GET("/api/industries/{id}")
    Observable<JsonObject> getCompanyIndustryInfo(
            @Query("id") String id
    );



    @GET("/api/organizations/search-info")
    Observable<JsonArray> getCompanyInfoMiddleList(
            @Query("name") String name
    );



}
