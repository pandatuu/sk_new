package com.example.sk_android.mvp.view.fragment.register;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.*;

public interface RegisterApi {
    // https://auth.sk.cgland.top/api/users/verify-code
    @Headers("Content-Type: application/json")
    @POST("/api/users/verify-code")
    Observable<String> getVerfiction(@Body RequestBody array);

    @Headers("Content-Type: application/json")
    @POST("/api/users/login")
    Observable<String> userLogin(@Body RequestBody array);
}
