package com.example.sk_android.mvp.view.fragment.register;

import com.google.gson.JsonObject;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.Map;

/**
 *  @author     wuqijie
 *  @date on    2019/05/28
 *  @describe   Registration related interface
 */
public interface RegisterApi {

    // https://auth.sk.cgland.top/api/users/verify-code
    @Headers("Content-Type: application/json")
    @POST("/api/users/verify-code")
    Observable<Response<String>> getVerification(@Body RequestBody array);

    // User login
    @Headers("Content-Type: application/json")
    @POST("/api/users/login")
    Observable<JsonObject> userLogin(@Body RequestBody array);

    // Check verification code
    @Headers("Content-Type: application/json")
    @POST("/api/users/validate-verify-code")
    Observable<Response<String>> checkVerification(@Body RequestBody array);


    // User register
    @Headers("Content-Type: application/json")
    @POST("/api/users/register")
    Observable<JsonObject> userRegister(@Body RequestBody array);

    // User find password
    @Headers("Content-Type: application/json")
    @PATCH("/api/users/find-password")
    Observable<Response<String>> findPassword(@Body RequestBody array);

    // Ordinary users use to get their own information
    @GET("/users/self")
    Observable<JsonObject> verifyUser();

    // Ordinary users use to create their own information
    @POST("/api/v1/users/")
    Observable<Response<String>> perfectPerson(@Body RequestBody array);

    // Update user's job search status
    @PATCH("/api/v1/users/job-state")
    Observable<Response<String>> UpdateWorkStatu(@Body RequestBody array);

    // Get a personal online resume (only)
    @GET("/api/v1/resumes/")
    Observable<JsonObject> getOnlineResume(@Query("type") String type);

    // Create a person online resume (only one)
    @POST("/api/v1/resumes/")
    Observable<Response<String>> createOnlineResume(@Body RequestBody array);
}
