package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.RelativeLayout
import android.widget.VideoView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI


class ResumeEditBackground : Fragment(){

    lateinit var backBtn : BackgroundBtn
    var imageUrl : String? = null
    var relative : RelativeLayout? = null
    lateinit var video : VideoView


    companion object {
        fun newInstance(url : String?): ResumeEditBackground {
            val fragment = ResumeEditBackground()
            fragment.imageUrl = url
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        backBtn = activity as BackgroundBtn
        var fragmentView = createView()

        return fragmentView
    }

    fun setVideo(){
        video.start()
    }
    fun setVideoGone(){
        video.visibility = View.GONE
    }
    private fun createView(): View? {
        val view = UI {
            relativeLayout{
                relative = relativeLayout {
                    backgroundResource = R.mipmap.job_photo_upload
                    if(imageUrl!=null && imageUrl!=""){
                        relativeLayout {
                            video = videoView {
                                setVideoURI(Uri.parse(imageUrl))
                            }.lparams(wrapContent, wrapContent){
                                centerInParent()
                            }
//                            video.start()
                            video.setOnCompletionListener(object : MediaPlayer.OnCompletionListener{
                                override fun onCompletion(mp: MediaPlayer?) {
                                    video.start()
                                }
                            })
                        }.lparams(matchParent, dip(270))
                    }
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