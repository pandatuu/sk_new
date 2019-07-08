package com.example.sk_android.utils;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dk on 2019/7/8
 */
public class PermissionManager {
    private static volatile PermissionManager instance;
    private static IPermissionResult iPermissionResult = new IPermissionResult() {
        @Override
        public void getPermissionFailed(Activity activity, int requestCode,String[] deniedPermissions) {

        }

        @Override
        public void getPermissionSuccess(Activity activity, int requestCode) {

        }
    };
    private Activity activity;
    private int REQUEST_CODE;
    private static final String TAG = "PermissionManager";

    private PermissionManager() {

    }

    public static PermissionManager init() {
        if (instance == null) {
            synchronized (PermissionManager.class) {
                if (instance == null) {
                    instance = new PermissionManager();
                }
            }
        }
        return instance;
    }

    public PermissionManager checkPermissions(Activity activity, int
            REQUEST_CODE, IPermissionResult result, String[]... permissions) {
        this.activity = activity;
        this.iPermissionResult = result;
        this.REQUEST_CODE = REQUEST_CODE;
        List< String > allPermission = new ArrayList<>();
        for (String[] permission1 : permissions) {
            for (String permission : permission1) {
                allPermission.add(permission);
            }
        }
        getPermission(activity, allPermission);
        return this;
    }


    private void getPermission(Activity activity, List< String > permissions) {
        List< String > applyPermission = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < permissions.size(); i++) {
                String permission = permissions.get(i);
                if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager
                        .PERMISSION_GRANTED) {
                    applyPermission.add(permission);
                }
            }
            checkPermissionResult(applyPermission);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissionResult(List< String > list) {
        String[] permissions = list.toArray(new String[list.size()]);
        if (list.size() > 0) {
            Log.d(TAG, "申请权限");
            ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE);
        } else {
            iPermissionResult.getPermissionSuccess(activity, REQUEST_CODE);
        }
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[]
            grantResults) {
        requestResult(activity, requestCode, permissions, grantResults);
    }

    private static void requestResult(Activity activity, int code, String[] permissions, int[] results) {
        List< String > deniedPermissions = new ArrayList<>();
        for (int i = 0; i < results.length; i++) {
            if (results[i] != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permissions[i]);
            }
        }
        if (deniedPermissions.size() > 0) {
            iPermissionResult.getPermissionFailed(activity, code,deniedPermissions.toArray(new String[deniedPermissions.size()]));
        } else {
            iPermissionResult.getPermissionSuccess(activity, code);
        }
    }
}
