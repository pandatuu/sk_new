package com.example.sk_android.mvp.api.jobselect;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import retrofit2.http.*;


public interface RecruitInfoApi {


    //得到职位信息列表
    //http://organization-position.sk.cgland.top
    //@Headers("Content-Type: application/json")
    @GET("/api/organization-positions/user")
    Observable<JsonObject> getRecruitInfoList(
                                            @Query("_page") Integer _page,
                                            @Query("_limit") Integer _limit,
                                            @Query("name") String name,
                                            @Query("recruitMethod") String recruitMethod,
                                            @Query("workingType") String workingType,
                                            @Query("workingExperience") Integer workingExperience,
                                            @Query("currencyType") String currencyType,
                                            @Query("salaryType") String salaryType,
                                            @Query("salaryMin") Integer salaryMin,
                                            @Query("salaryMax") Integer salaryMax,
                                            @Query("auditState") String auditState,
                                            @Query("educationalBackground") String educationalBackground,
                                            @Query("industryId") String industryId,
                                            @Query("address") String address,
                                            @Query("radius") Number radius
                                            );

    //根据Id查询公司详细信息
    @GET("/api/organizations/{organization-id}")
    Observable<JsonObject> getCompanyInfo(
                                            @Path("organization-id") String organizationId
    );




    //查询中间过度列表
    @GET("/api/organization-positions/position-list")
    Observable<JsonArray> getRecruitInfoMiddleList(
            @Query("name") String name
    );



    //根据Id查询职位信息
    @GET("/api/organization-positions/{id}")
    Observable<JsonObject> getRecruitInfoById(
            @Path("id") String id
    );





}
