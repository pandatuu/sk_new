package com.example.sk_android.mvp.view.fragment.register;

import com.google.gson.JsonObject;
import io.reactivex.Observable;
import okhttp3.RequestBody;
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
    Observable<String> getVerification(@Body RequestBody array);

    // User login
    @Headers("Content-Type: application/json")
    @POST("/api/users/login")
    Observable<JsonObject> userLogin(@Body RequestBody array);

    // Check verification code
    @Headers("Content-Type: application/json")
    @POST("/api/users/validate-verify-code")
    Observable<String> checkVerification(@Body RequestBody array);


    // User register
    @Headers("Content-Type: application/json")
    @POST("/api/users/register")
    Observable<JsonObject> userRegister(@Body RequestBody array);

    // User find password
    @Headers("Content-Type: application/json")
    @PATCH("/api/users/find-password")
    Observable<String> findPassword(@Body RequestBody array);

    // Improve personal information one
    @GET("/users/self")
    Observable<JsonObject> verifyUser();
}
