package com.example.sk_android.utils;


import android.app.Activity;

/**
 * Created by dk on 2019/7/8
 */
public interface IPermissionResult {
    void getPermissionFailed(Activity activity, int requestCode, String[] deniedPermissions);

    void getPermissionSuccess(Activity activity, int requestCode);
}