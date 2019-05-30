package com.example.sk_android.mvp.tool;

import com.orhanobut.logger.Logger;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RetrofitUtils {
    /*
    http://api.douban.com/
     */
//    private String baseUrl = "https://auth.sk.cgland.top/";

    private static String baseUrl;
    private static final RetrofitUtils retrofitUtils = new RetrofitUtils(baseUrl);

    public static RetrofitUtils get(){
        return retrofitUtils;
    }
    private static volatile Retrofit retrofit;
    public RetrofitUtils(String baseUrl){
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()));

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder request = chain.request().newBuilder();
//                        request.addHeader("Accept","*/*");
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