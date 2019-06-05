package com.example.sk_android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.example.sk_android.mvp.view.activity.register.LoginActivity;
import com.example.sk_android.mvp.view.fragment.register.LoginMainBodyFragment;
import com.orhanobut.logger.Logger;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RetrofitUtils {

//    private String baseUrl = "https://auth.sk.cgland.top/";
    public String baseUrl;

    SharedPreferences mPerferences;

    private static volatile Retrofit retrofit;

    public RetrofitUtils(Context context, String baseUrl){
        this.baseUrl = baseUrl;
        this.mPerferences = PreferenceManager.getDefaultSharedPreferences(context);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder request = chain.request().newBuilder();
                        System.out.println("1-------------------4");
                        System.out.println(mPerferences.getString("token",null));
                        System.out.println("1-------------------5");
                        System.out.println(chain.request());
//                        request.addHeader("Accept","*/*");
                        request.addHeader("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI2NDZkNjgxMS1lYWRlLTQ4N2QtODhjYi1hNTZjMTIwMzMxY2EiLCJ1c2VybmFtZSI6ImxpemhlbmNodWFuIiwidGltZXN0YW1wIjoxNTU5NTQxNzg5OTc1LCJpYXQiOjE1NTk1NDE3ODl9.veMqePNpWbTpQPyWMqTU-8Kb-FjCD_uvIdPJNTSqeMD4PcykTdAJYQIJfkYeqv1eP64WfFltgm0OXdtSpppG3JWfyrK0VHt7R_UdU4yV97rK5CLKp8Ax4-cB_EUZx8Hm63mviJ_BsToV7n1rcc1SI_-CUdMJTIobUlcBPc_J0UuRVhFhkD2bLN1bw1LCDbAj25Qm17EUpot0Tre4OZGeqi3ugbkOscY_08f-_gp-EOuhhiEGfi8M64u1Azslcw41VdHkmeEWPqJMh0fNqC4ttNej3Dzg5bzqdn67pawD2qG8qqw0upIcn4ZOQRCxUuRV6hPG-vhxA02AOMJjKepebQ");
                        //添加拦截器
                        return chain.proceed(request.build());
                    }
                });

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.i(message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        retrofit = retrofitBuilder.client(builder.build()).build();
    }

    public  <T> T create(Class<T> cls) {
        return retrofit.create(cls);
    }

//    private static final RetrofitUtils retrofitUtils = new RetrofitUtils();

//    public static RetrofitUtils get(){
//        return new RetrofitUtils();
//    }

//    public void setBaseUrl(String baseUrl) {
//        this.baseUrl = baseUrl;
//    }


}