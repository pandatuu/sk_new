package com.example.sk_android.mvp.view.activity.common


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.umeng.message.PushAgent


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
