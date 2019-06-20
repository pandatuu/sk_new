package com.example.sk_android.mvp.view.fragment.person;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.reactivex.Observable;
import okhttp3.RequestBody;
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

    // get person Interview information
    @GET("/api/interview-agendas/mine")
    Observable<JsonObject> gitInterviewInformation();

    // Get user job status
    @GET("api/v1/users/job-state")
    Observable<JsonObject> getJobStatu();

    // Update user information
    @PUT("/api/v1/users/")
    Observable<Response<String>> updatePerson(@Body RequestBody array);


}
