package com.example.sk_android.mvp.view.activity.company

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.Gravity
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import click
import cn.jiguang.imui.messages.ptr.PullToRefreshLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.common.BaseActivity
import com.example.sk_android.utils.DialogUtils
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*
import withTrigger


class VideoShowActivity : BaseActivity() {

    override fun onStart() {
        super.onStart()
        StatusBarUtil.setTranslucentForImageView(
            this@VideoShowActivity,
            0,
            null
        )
    }

    val mainId = 1
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFormat(PixelFormat.OPAQUE)
        var url = ""
        if(intent.getStringExtra("url")!=null){
            url = intent.getStringExtra("url")
        }
        DialogUtils.showLoadingClick(this@VideoShowActivity)
        frameLayout {
            id = mainId
            backgroundColor = Color.BLACK

            lateinit var video: VideoView
            lateinit var image: ImageView

            frameLayout {
                backgroundColor = Color.BLACK

                video = videoView {
                    setVideoURI(Uri.parse(url))
                }.lparams(dip(1), dip(1)) {
                    gravity = Gravity.CENTER
                }
                val mControl = MediaController(this@VideoShowActivity)
                mControl.setMediaPlayer(video)
                video.setMediaController(mControl)
                image = imageView {

                }.lparams(dip(125),dip(125)){
                    gravity = Gravity.CENTER
                }

                video.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {

                    override fun onPrepared(mp: MediaPlayer?) {
                        DialogUtils.hideLoading()
                        var layout= video.layoutParams
                        layout.width=PullToRefreshLayout.LayoutParams.MATCH_PARENT
                        layout.height=PullToRefreshLayout.LayoutParams.WRAP_CONTENT
                        video.layoutParams=layout
                        video.start()

                    }

                })
                video.setOnErrorListener(object: MediaPlayer.OnErrorListener{
                    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                        video.stopPlayback()
                        toast("ローディング失敗")
                        DialogUtils.hideLoading()
                        image.imageResource = R.mipmap.no_network
                        return true
                    }
                })

            }.lparams {
                height=matchParent
                width=matchParent
                gravity = Gravity.CENTER
            }



//                this.withTrigger().click {
//
//                    var layout= video.layoutParams
//
//                    layout.width=dip(1)
//                    layout.height=dip(1)
//                    video.layoutParams=layout
//
//
//
//                    finish()//返回
//                    overridePendingTransition(R.anim.left_in,R.anim.right_out)
//                }

           


        }


    }
   

}
