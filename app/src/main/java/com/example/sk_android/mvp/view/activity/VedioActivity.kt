package com.example.sk_android.mvp.view.activity

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.SurfaceView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.VideoView
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.ShareAction
import com.umeng.socialize.bean.SHARE_MEDIA
import org.jetbrains.anko.button
import org.jetbrains.anko.dip
import org.jetbrains.anko.linearLayout
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk25.coroutines.onClick
import android.support.v4.app.ActivityCompat
import android.Manifest.permission
import android.Manifest.permission.WRITE_APN_SETTINGS
import android.Manifest.permission.GET_ACCOUNTS
import android.Manifest.permission.SYSTEM_ALERT_WINDOW
import android.Manifest.permission.SET_DEBUG_APP
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_PHONE_STATE
import android.Manifest.permission.READ_LOGS
import android.Manifest.permission.CALL_PHONE
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.os.Build
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.shareboard.SnsPlatform
import com.umeng.socialize.utils.ShareBoardlistener
import com.twitter.sdk.android.core.TwitterCore
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.tweetcomposer.TweetComposer






/**
 * 视频播放界面
 */
class VedioActivity : AppCompatActivity() {

    private lateinit var rela: SurfaceView
    lateinit var video : VideoView
    lateinit var image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (Build.VERSION.SDK_INT >= 23) {
            val mPermissionList = arrayOf<String>(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS
            )
            ActivityCompat.requestPermissions(this, mPermissionList, 123)
        }


        linearLayout {
            linearLayout {
                orientation = LinearLayout.HORIZONTAL
                button{
                    text = "line"
                    onClick {
                        ShareAction(this@VedioActivity)
                            .setPlatform(SHARE_MEDIA.LINE)//传入平台
                            .withText("hello")//分享内容
                            .setShareboardclickCallback { _, _ -> println("11111111111111111111111111111111111111111 ") }
                            .share()
                    }
                }
                button {
                    text = "twitter"
                    onClick {
                        val builder = TweetComposer.Builder(this@VedioActivity)
                        builder.text("hello world")
                            .show()
                    }
                }
            }.lparams(matchParent, dip(200))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {

    }

}
