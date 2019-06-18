package com.example.sk_android.mvp.api.jobselect;

import com.google.gson.JsonObject;
import io.reactivex.Observable;
import retrofit2.http.*;


public interface RecruitInfoApi {



    //http://organization-position.sk.cgland.top
    //@Headers("Content-Type: application/json")
    @GET("/api/organization-positions/user")
    Observable<JsonObject> getRecruitInfoList(
//                                            @Query("_page") Integer _page,
//                                            @Query("_limit") Integer _limit,
//                                            @Query("recruitMethod") String recruitMethod,
//                                            @Query("workingType") String workingType,
//                                            @Query("workingExperience") Integer workingExperience,
//                                            @Query("currencyType") String currencyType,
//                                            @Query("salaryType") String salaryType,
//                                            @Query("salaryMin") Integer salaryMin,
//                                            @Query("salaryMax") Integer salaryMax,
//                                            @Query("auditState") String auditState,
//                                            @Query("educationalBackground") String educationalBackground,
//                                            @Query("industryId") String industryId,
//                                            @Query("address") String address,
//                                            @Query("radius") Number radius
                                            );


}
