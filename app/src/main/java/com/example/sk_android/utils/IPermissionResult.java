package com.example.sk_android.utils;


import android.app.Activity;

/**
 * Created by dk on 2018/6/7
 */
public interface IPermissionResult {
    void getPermissionFailed(Activity activity, int requestCode, String[] deniedPermissions);

    void getPermissionSuccess(Activity activity, int requestCode);
}