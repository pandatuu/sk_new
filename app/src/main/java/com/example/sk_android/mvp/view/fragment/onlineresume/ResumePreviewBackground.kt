package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.media.session.MediaControllerCompat
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
import android.view.*
import android.widget.VideoView
import com.example.sk_android.custom.layout.MyPicker.Companion.TAG
import kotlinx.android.synthetic.main.radion.view.*


class ResumePreviewBackground : Fragment() {

    lateinit var backBtn : BackgroundBtn
    var imageUrl : String? = null
    var relative : RelativeLayout? = null
    var bool = false
    lateinit var video : VideoView


    companion object {
        fun newInstance(url : String?,boolean : Boolean): ResumePreviewBackground {
            val fragment = ResumePreviewBackground()
            fragment.imageUrl = url
            fragment.bool = boolean
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        backBtn = activity as BackgroundBtn
        var fragmentView = createView()

        return fragmentView
    }
//
//    fun setUrl(url: String){
//        video.setVideoURI(Uri.parse(url))
//        relative?.addView(video)
//    }

    private fun createView(): View? {
        val view = UI {
            relativeLayout{
                relative = relativeLayout {
                    backgroundResource = R.mipmap.job_photo_upload
                    if(imageUrl!=null && imageUrl!=""){
                        relativeLayout {
                            video = videoView {
                                setVideoURI(Uri.parse(imageUrl))
                                setMediaController(MediaController(context))
                                
                            }.lparams(wrapContent, wrapContent){
                                centerInParent()
                            }
                            val vController = MediaController(context)
                            video.setMediaController(vController)
                            vController.setMediaPlayer(video)
                            if(bool){
                                vController.setVisibility(View.GONE)
                            }
                            video.start()
                            video.setOnCompletionListener(object : MediaPlayer.OnCompletionListener{
                                override fun onCompletion(mp: MediaPlayer?) {
                                    toast("111111")
                                    video.start()
                                }
                            })
                        }.lparams(matchParent, dip(270))
                    }
                    if(bool){
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