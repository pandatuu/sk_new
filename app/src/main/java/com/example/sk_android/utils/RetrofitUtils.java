package com.example.sk_android.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.orhanobut.logger.Logger;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
                        request.addHeader("Authorization", "Bearer " + mPerferences.getString("token",null));
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


}