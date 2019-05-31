package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.media.session.MediaControllerCompat
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.RelativeLayout
import com.example.sk_android.R
import com.universalvideoview.UniversalMediaController
import com.universalvideoview.UniversalVideoView
import kotlinx.android.synthetic.main.roll_choose.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import android.util.Log
import com.example.sk_android.custom.layout.MyPicker.Companion.TAG
import kotlinx.android.synthetic.main.radion.view.*


class ResumePreviewBackground : Fragment() {

    lateinit var backBtn : BackgroundBtn
    var imageUrl : String? = null
    var relative : RelativeLayout? = null


    companion object {
        fun newInstance(url : String?): ResumePreviewBackground {
            val fragment = ResumePreviewBackground()
            fragment.imageUrl = url
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        backBtn = activity as BackgroundBtn
        var fragmentView = createView()

        return fragmentView
    }

    private fun createView(): View? {
        val view = UI {
            relativeLayout{
                relative = relativeLayout {
                    backgroundResource = R.mipmap.job_photo_upload
                    //获取url的后缀
//                    val cut = imageUrl!!.substring(imageUrl!!.length-3,imageUrl!!.length)
                    if(imageUrl!=null){
//                        backgroundResource = imageButton()
                        relativeLayout {
                            var video = videoView {
                                setVideoURI(Uri.parse(imageUrl))
                                setMediaController(MediaController(context))
                            }.lparams(matchParent, matchParent){
                                centerInParent()
                            }
                            val vController = MediaController(context)
                            video.setMediaController(vController)
                            vController.setMediaPlayer(video)
                            video.start()
                            video.setOnCompletionListener(object : MediaPlayer.OnCompletionListener{
                                override fun onCompletion(mp: MediaPlayer?) {
                                    toast("111111")
                                }
                            })
                        }.lparams(matchParent, dip(270))
                    }else{
                        button {
                            backgroundResource = R.drawable.fifteen_radius_button
                            text = "仕事の写真/ビデオをアップロド"
                            textSize = 10f
                            textColor = Color.WHITE
                            onClick {
                                toast("yeah!!!!!!!!!!!!")
                                backBtn.clickButton()
                            }
                        }.lparams{
                            width = dip(170)
                            height = dip(35)
                            centerInParent()
                        }
                    }
                }.lparams {
                    width = matchParent
                    height = dip(370)
                }
            }
        }.view
        return view
    }

    interface BackgroundBtn{
        fun clickButton()
    }
}