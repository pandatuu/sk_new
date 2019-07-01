package com.example.sk_android.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.orhanobut.logger.Logger
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.apache.commons.lang.StringUtils
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitUtils(
    context: Context, //    private String baseUrl = "https://auth.sk.cgland.top/";
    baseUrl: String
) {

    companion object {

        private lateinit var retrofit: Retrofit

    }

    private val mPerferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))

        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                println(chain.request())

                val accessToken = mPerferences.getString("token", "")

                if(accessToken.isNotBlank()){
                    request.addHeader(
                        "Authorization",
                        "Bearer ${accessToken.replace("\"","")}")
                }

//                if(accessToken.isBlank()){
//                    request.addHeader(
//                        "Authorization",
//                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiI1NGUyYjM3NS04ZDI5LTRjNWMtOGQwMS04OWYwMzQ5ZThjZDQiLCJ1c2VybmFtZSI6Ijg2MTM2OTM0NTAyNzAiLCJ0aW1lc3RhbXAiOjE1NjEzNDY4ODUxNTMsImRldmljZVR5cGUiOiJXRUIiLCJpYXQiOjE1NjEzNDY4ODV9.lmeOO5it05vQ2ikSOAvE90mguhfR4hZgtORlcDI8ZrYxVsA5p3n7r-Oji0VZShZiK8EKJboSWMeh57WoXtqPeJehgUPJCFtebJPRD13nEIXyb41LXRy_X3bXFMpbu6p_74isdrgj520tUxD5EB2HQKoPm6rqndAtm49na14GFwOCgRTe_ZJXdenRNhx7SK57S0yjsHc1YJuljZ8Z8R5q0aG7R7ryeOzYWzEBTk-m1pIOQUMT60yDxb0JifK1CpQPuAKiz9-0yOVsA-mMIdBr_Ks8UZOmz1yBmfkny3Zrc7pcu7X3dM6ZrWFhZK4IUFQXf-3ziZLGBUAunEXLl-S6yw")
//                }



                println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx")
                println(request.build().headers().get("Authorization"))
                //添加截器
                chain.proceed(request.build())
            }

        val loggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Logger.i(message) }).setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        builder.addInterceptor(loggingInterceptor)

        retrofit = retrofitBuilder.client(builder.build()).build()
    }

    fun <T> create(cls: Class<T>): T {
        return retrofit.create(cls)
    }

}
