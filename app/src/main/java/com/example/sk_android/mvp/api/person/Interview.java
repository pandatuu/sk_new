package com.example.sk_android.mvp.api.person;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.*;

public interface Interview {

    @Headers("Content-Type: application/json")
    @GET("/interview-agendas/mine")
    Observable<String> getMyInterviewList(
            @Query("_page")  Integer  _page,
            @Query("_limit") Integer  _limit,
            @Query("state")  String   state,
            @Query("is-recruiter") Boolean isRecruiter
    );


}
