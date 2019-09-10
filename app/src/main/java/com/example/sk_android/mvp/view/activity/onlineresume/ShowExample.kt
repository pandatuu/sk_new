package com.example.sk_android.mvp.view.activity.onlineresume

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.MediaController
import cn.jiguang.imui.messages.ptr.PullToRefreshLayout
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.view.fragment.onlineresume.ShowExampleFrag
import com.example.sk_android.utils.DialogUtils
import com.jaeger.library.StatusBarUtil
import org.jetbrains.anko.*

class ShowExample : AppCompatActivity() {

    var thisDialog: MyDialog? = null
    private var mainId = 1
    var actionBarNormalFragment: ShowExampleFrag? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            id = mainId
            verticalLayout {
                val actionBarId = 3
                frameLayout {
                    id = actionBarId
                    actionBarNormalFragment = ShowExampleFrag.newInstance()
                    supportFragmentManager.beginTransaction().replace(id, actionBarNormalFragment!!).commit()
                }.lparams {
                    height = wrapContent
                    width = matchParent
                }


                linearLayout {
                    thisDialog = DialogUtils.showLoadingClick(this@linearLayout.context)
                    val video = videoView {
                        setVideoURI(Uri.parse("https://sk-user-resume-video.s3.ap-northeast-1.amazonaws.com/346f1182-a1bb-49dd-8fcd-c597ad09d51f.mp4"))
                    }.lparams(matchParent, matchParent)
                    val mControl = MediaController(this@ShowExample)
                    mControl.setMediaPlayer(video)
                    video.setMediaController(mControl)

                    video.setOnPreparedListener {
                        DialogUtils.hideLoading(thisDialog)
                        val layout = video.layoutParams
                        layout.width = PullToRefreshLayout.LayoutParams.MATCH_PARENT
                        layout.height = PullToRefreshLayout.LayoutParams.WRAP_CONTENT
                        video.layoutParams = layout
                        video.start()
                    }
                    video.setOnErrorListener(object : MediaPlayer.OnErrorListener {
                        override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                            video.stopPlayback()
                            toast("ローディング失敗")
                            DialogUtils.hideLoading(thisDialog)
                            return true
                        }
                    })
                }

            }
        }
    }

    override fun onStart() {
        super.onStart()
        setActionBar(actionBarNormalFragment!!.toolbar1)
        StatusBarUtil.setTranslucentForImageView(this@ShowExample, 0, actionBarNormalFragment!!.toolbar1)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        actionBarNormalFragment!!.toolbar1!!.setNavigationOnClickListener {
            val intent = Intent(this@ShowExample, ResumeEdit::class.java)//返回
            startActivity(intent)
            finish()
            overridePendingTransition(R.anim.left_in, R.anim.right_out)
        }
    }


}