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
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.PermissionManager


open class BaseActivity : AppCompatActivity() , ActivityCompat.OnRequestPermissionsResultCallback{

    private val PERMISSIONS_REQUEST_CAMERA = 0
    private val PERMISSIONS_REQUEST_READ_PHONE_STATE=1
    private val mLayout: View? = null



    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //接受推送
        PushAgent.getInstance(this).onAppStart()
        checkPermission()
    }



    override fun onDestroy() {
        super.onDestroy()
        DialogUtils.hideLoading()
    }


    fun checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            requestReadPhoneState();
        }
    }



    private fun requestReadPhoneState(){

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE),
                PERMISSIONS_REQUEST_READ_PHONE_STATE)
    }


    private fun requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
//            Snackbar.make(mLayout, R.string.permission_camera_rationale,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.ok, object : View.OnClickListener() {
//                        fun onClick(view: View) {
//                            ActivityCompat.requestPermissions(this@MainActivity,
//                                    arrayOf(Manifest.permission.CAMERA),
//                                    REQUEST_CAMERA)
//                        }
//                    })
//                    .show()
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                    PERMISSIONS_REQUEST_CAMERA)
        } else {

            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                    PERMISSIONS_REQUEST_CAMERA)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                           grantResults: IntArray) {

        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {

            // Received permission result for camera permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Camera permission has been granted, preview can be displayed

            } else {


            }
        }
    }


}
