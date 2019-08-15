package com.example.sk_android.mvp.api.company;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.*;


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
                                            @Query("type") String type,//公司类型
                                            @Query("coordinate") String coordinate,//坐标
                                            @Query("radius") Number radius,//半径
                                            @Query("industry-ids") String industryId,//行业
                                            @Query("area-id") String areaId//地区ID



    );


    @GET("/api/industries/{id}")
    Observable<JsonObject> getCompanyIndustryInfo(
            @Path("id") String id
    );

    @GET("/api/organizations/{organizationId}")
    Observable<Response<JsonObject>> getCompanyById(
            @Path("organizationId") String id
    );

    @GET("/api/organizations/{organizationId}/addresses")
    Observable<Response<JsonObject>> getCompanyAddressById(
            @Path("organizationId") String id
    );

    //创建点赞公司信息
    @POST("/api/user-organization-praise-histories")
    Observable<Response<String>> createCompanyDianZan(
            @Body RequestBody body
    );
    //获取公司点赞数
    @GET("/api/user-organization-praise-histories/{organizationId}/count-praise")
    Observable<Response<Integer>> getCompanyDianZan(
            @Path("organizationId") String id
    );
    //获取是否点赞公司
    @GET("/api/user-organization-praise-histories/{organizationId}/praised")
    Observable<Response<Boolean>> isDianZan(
            @Path("organizationId") String id
    );

    @GET("/api/organizations/search-info")
    Observable<JsonArray> getCompanyInfoMiddleList(
            @Query("name") String name
    );

    //根据ID获取区域
    @GET("/api/areas/{id}")
    Observable<Response<JsonObject>> getAreaById(
            @Path("id") String id
    );
    //根据ID获取父级区域
    @GET("/api/areas/{id}/children")
    Observable<Response<JsonObject>> getAreaParentById(
            @Path("id") String id
    );


    //获取一个公司下面有多少个职位
    @GET("/api/organization-positions/position-count")
    Observable<JsonObject> getPositionNumberOfCompany(
            @Query("organization-id") String id
    );

    //获取用户所有举报信息
    @GET("/api/reports/self-record")
    Observable<Response<JsonObject>> getReportsById();

    //
    @GET("/api/areas/{id}")
    Observable<Response<JsonObject>> getAreaInfo(
            @Path("id") String id
    );
}
