package com.example.sk_android.mvp.tool;

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}
