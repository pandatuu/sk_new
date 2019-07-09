package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.RelativeLayout
import android.widget.VideoView
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.company.VideoShowActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI


class ResumePreviewBackground : Fragment() {

    lateinit var backBtn : BackgroundBtn
    var imageUrl : String? = null
    var relative : RelativeLayout? = null
    var videoRela: RelativeLayout? = null
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
                        videoRela = relativeLayout {
                            linearLayout(){
                                gravity= Gravity.CENTER
                                image = imageView {
                                    imageResource = R.mipmap.player
                                    this.withTrigger().click {
                                        val intent = Intent(activity!!, VideoShowActivity::class.java)
                                        intent.putExtra("url", imageUrl)
                                        startActivity(intent)
                                        activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                                    }
                                }.lparams(dip(70), dip(70)) {
                                }
                            }.lparams {
                                width= matchParent
                                height= matchParent
                            }

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