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

//                if(accessToken.isNotBlank()){
//                    request.addHeader(
//                        "Authorization",
//                        "Bearer ${accessToken.replace("\"","")}")
//                }
                    request.addHeader(
                        "Authorization",
                        "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIyMDU3MTE1Yi1iMzRkLTQ2YzYtYTIzNy1kMmYxOWE1Nzg0OTMiLCJ1c2VybmFtZSI6Ijg2MTUxMTAzMTcwMjEiLCJ0aW1lc3RhbXAiOjE1NjAyMTkzODEwNDQsImRldmljZVR5cGUiOiJBTkRST0lEIiwiaWF0IjoxNTYwMjE5MzgxfQ.Df_1HDiRGOJW82UmmMqYVgzaGya7rccVM2rc94JPIfprQjuaMs_JQnYFbtMb-DjnDYVgo7k2qVvjwqtrDPZz4-CaxrBACCZtH2QDtCgbSRw5VZ3YJzHXqW_AnPhRUn00KhIhg1sHNkItNq1TUgX_kis2MkN4yXXejpcio-DZsYWlmXdYqm93C9R-_EWM1gUDmnLfWxTh6B4yVEANOXTYl_5ndHTlLoWav0S-9wW1_2zzU0D1WoSHJTEfsxyj2Dz6ez7OkyzXzn8I6qGA5b6uChcI4uuGdle0UAzGV5xLHiaWWuSRlr7CZQOqXO_gQi15ocROdT6gi8qK__o_ZknylA")

                //添加拦截器
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
