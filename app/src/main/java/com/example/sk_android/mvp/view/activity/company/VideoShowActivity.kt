package com.example.sk_android.mvp.view.activity.company

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.VideoView
import cn.jiguang.imui.messages.ptr.PullToRefreshLayout
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.api.company.CompanyInfoApi
import com.example.sk_android.mvp.model.company.CompanyInfo
import com.example.sk_android.mvp.view.activity.common.AccusationActivity
import com.example.sk_android.mvp.view.fragment.common.BottomSelectDialogFragment
import com.example.sk_android.mvp.view.fragment.common.ShadowFragment
import com.example.sk_android.mvp.view.fragment.common.TipDialogFragment
import com.example.sk_android.mvp.view.fragment.company.CompanyInfoSelectbarFragment
import com.example.sk_android.mvp.view.fragment.jobselect.CompanyDetailActionBarFragment
import com.example.sk_android.mvp.view.fragment.jobselect.CompanyDetailInfoFragment
import com.example.sk_android.mvp.view.fragment.mysystemsetup.LoginOutFrag
import com.example.sk_android.utils.RetrofitUtils
import com.google.gson.JsonObject
import com.jaeger.library.StatusBarUtil
import com.umeng.message.PushAgent
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import org.jetbrains.anko.*


class VideoShowActivity : AppCompatActivity() {

    override fun onStart() {
        super.onStart()
        StatusBarUtil.setTranslucentForImageView(
            this@VideoShowActivity,
            0,
            null
        )
    }

    private var myDialog: MyDialog? = null
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart()
        getWindow().setFormat(PixelFormat.OPAQUE)
        showLoading("")
        linearLayout() {


            backgroundColor = Color.BLACK
            gravity = Gravity.CENTER

            lateinit var video: VideoView

            linearLayout {
                backgroundColor = Color.BLACK
                gravity = Gravity.CENTER

                video = videoView {
                    setVideoURI(Uri.parse(intent.getStringExtra("url")))
                }.lparams(dip(1), dip(1)) {
                }


                video.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {

                    override fun onPrepared(mp: MediaPlayer?) {
                        hideLoading()
                        var layout= video.layoutParams
                        layout.width=PullToRefreshLayout.LayoutParams.MATCH_PARENT
                        layout.height=PullToRefreshLayout.LayoutParams.WRAP_CONTENT
                        video.layoutParams=layout
                        video.start()

                    }

                })

            }.lparams {
                height=matchParent
                width=matchParent
            }



            setOnClickListener(object : View.OnClickListener {

                override fun onClick(v: View?) {

                    var layout= video.layoutParams

                    layout.width=dip(1)
                    layout.height=dip(1)
                    video.layoutParams=layout



                    finish()//返回
                    overridePendingTransition(R.anim.right_out, R.anim.right_out)
                }

            })


        }


    }
    //关闭等待转圈窗口
    private fun hideLoading() {
        if (myDialog != null) {
            if (myDialog!!.isShowing()) {
                myDialog!!.dismiss()
                myDialog = null
            }
        }
    }




    private fun showLoading(str: String) {
        if (myDialog != null && myDialog!!.isShowing()) {
            myDialog!!.dismiss()
            myDialog = null
            val builder = MyDialog.Builder(this)
                .setCancelable(true)
                .setCancelOutside(true)
            myDialog = builder.create()

        } else {
            val builder = MyDialog.Builder(this)
                .setCancelable(true)
                .setCancelOutside(true)

            myDialog = builder.create()
        }

        myDialog!!.setOnCancelListener(object : DialogInterface.OnCancelListener{

            override fun onCancel(dialog: DialogInterface?) {

                finish()//返回
                overridePendingTransition(R.anim.right_out, R.anim.right_out)

            }

        })

        myDialog!!.show()


    }

}
