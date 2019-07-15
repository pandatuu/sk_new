package com.example.sk_android.mvp.view.activity.common


import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity;
import com.umeng.message.PushAgent
import android.support.design.widget.Snackbar
import android.content.pm.PackageManager
import android.R.string.ok
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import android.view.View
import com.example.sk_android.R
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.PermissionManager


open class ExitActivity : AppCompatActivity() , ActivityCompat.OnRequestPermissionsResultCallback{


    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //接受推送
        PushAgent.getInstance(this).onAppStart()
        System.exit(0)
    }



    override fun onDestroy() {
        super.onDestroy()
        System.exit(0)
    }




}
