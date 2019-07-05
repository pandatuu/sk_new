package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.RelativeLayout
import android.widget.VideoView
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI


class ResumePreviewBackground : Fragment() {

    lateinit var backBtn : BackgroundBtn
    var imageUrl : String? = null
    var relative : RelativeLayout? = null
    lateinit var video : VideoView
    lateinit var image : ImageView


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
                                visibility = View.INVISIBLE
                            }.lparams(matchParent, wrapContent){
                                centerInParent()
                            }
                            video.setOnCompletionListener(object : MediaPlayer.OnCompletionListener {
                                override fun onCompletion(mp: MediaPlayer?) {
                                    image.visibility = View.VISIBLE
                                }
                            })
                            var bool = false
                            var index = 0
                            image = imageView {
                                imageResource = R.mipmap.player
                                onClick {
                                    if (index == 0) {
                                        video.visibility = View.VISIBLE
                                    }
                                    index = 1
                                    visibility = View.INVISIBLE
                                    video.start()
                                    bool = true
                                }
                            }.lparams(dip(70), dip(70)) {
                                centerInParent()
                            }
                            video.setOnErrorListener(object: MediaPlayer.OnErrorListener{
                                override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
                                    video.stopPlayback()
                                    video.visibility = View.GONE
                                    image.visibility = View.VISIBLE
                                    toast("视频加载失败")
                                    return true
                                }

                            })
                            video.onClick {
                                if (bool) {
                                    image.visibility = View.VISIBLE
                                    video.pause()
                                    bool = false
                                } else {
                                    image.visibility = View.INVISIBLE
                                    video.start()
                                    bool = true
                                }
                            }
                        }.lparams(matchParent, dip(270))
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