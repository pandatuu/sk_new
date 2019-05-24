package com.example.sk_android.mvp.tool;


import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpUtil{
    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)
            .readTimeout(5, TimeUnit.MINUTES)
            .build();

    public static void okPost(String url, String json) throws IOException {
        System.out.println("2------------2");
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        System.out.println(json);
        RequestBody body = RequestBody.create(JSON, json);
        System.out.println(url);
        System.out.println("1--------------------------1");
        System.out.println(body);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
//        Call call = okHttpClient.newCall(request);

        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> e) throws Exception {
                Response response = okHttpClient.newCall(request).execute();
                e.onSuccess(response.body().string());
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s);
                    }
                });

//        Response response = okHttpClient.newCall(request).execute();
//        System.out.println("3--------3");
//        System.out.println(response);

//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                System.out.println("失败了");
//                System.out.println("e");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String string = response.body().string();
//                System.out.println(string);
//            }
//        });
//        Response response = okHttpClient.newCall(request).execute();
//        String res = response.body().string();
//        System.out.println(res);
    }
}
