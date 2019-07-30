package com.example.sk_android.mvp.view.fragment.person;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import org.json.JSONObject;
import retrofit2.Response;
import retrofit2.http.*;

/**
 *  @author     wuqijie
 *  @date on    2019/06/18
 *  @describe   Registration related interface
 */
public interface PersonApi {

    // get own person information
    @GET("/api/v1/users/self")
    Observable<JsonObject> getInformation();

    // Get user job status
    @GET("api/v1/users/job-state")
    Observable<JsonObject> getJobStatu();

    // Update user information
    @PUT("/api/v1/users/")
    Observable<Response<String>> updatePerson(@Body RequestBody array);

    // Get personal collection
    @GET("/api/v1/favorites/")
    Observable<JsonObject> getPersonFavorites(@Query("type")String type);

    // Get personal interview information
    @GET("/api/interview-agendas/mine")
    Observable<JsonObject> getPersonInformation(@Query("is-recruiter")boolean condition);

    // Get exchanged information
    @GET("/api/info-exchanges/mine")
    Observable<JsonObject> getExchangedInformation(@Query("state")String state);

    // Delete personal job search intention
    @DELETE("/api/v1/job-intentions/{id}")
    Observable<Response<String>> deleteJobIntention(@Path("id")String id);

    // Update person job search intention
    @PATCH("/api/v1/job-intentions/{id}")
    Observable<Response<String>> updateJobIntention(@Path("id")String id,@Body RequestBody array);

    // Get interview information by id
    @GET("/api/interview-agendas/{id}")
    Observable<JsonObject> getInterViewById(@Path("id")String id);

    // Get company name by id
    @GET("/api/organizations/{organization-id}")
    Observable<JsonObject> getCompanyName(@Path("organization-id")String id);

    // Get position name by id
    @GET("/api/organization-positions/{id}")
    Observable<JsonObject> getPositionName(@Path("id")String id);

    // Modify the status of the interview schedule
    @PUT("/api/interview-agendas/{id}/state")
    Observable<Response<String>> changeInterViewSchedule(@Path("id")String id,@Body RequestBody array);

    // get other person information by user id
    @GET("/api/v1/users/{id}")
    Observable<JsonObject> getOtherPersonById(@Path("id")String id);
    // Get personal interview information
    @GET("/api/organization-positions/{id}")
    Observable<Response<JsonObject>> getPositionById(@Path("id") String id);

    // Get exchanged information
    @GET("/api/info-exchanges/mine")
    Observable<JsonObject> getExchangesInfo(@Query("state") String state);

    // Get Favorites Job
    @GET("/api/v1/favorites/")
    Observable<Response<JsonObject>> getFavoritesJob(@Query("type") String state);

    // find address by company id
    @GET("/api/organizations/{organization-id}/addresses")
    Observable<JsonObject> getAddressByCompanyId(@Path("organization-id") String id);

    // send resume to others mail
    @POST("/api/mails")
    Observable<Response<String>> sendResume(@Body RequestBody array);

    // 获取地区id
    @GET("/areas")
    Observable<JsonArray> getAddressId(@Query("_top") Boolean top,@Query("name") String name);
}
