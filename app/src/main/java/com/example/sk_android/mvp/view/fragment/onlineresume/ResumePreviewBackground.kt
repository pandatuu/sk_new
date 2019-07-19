package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Intent
import android.graphics.Color
import android.media.MediaMetadataRetriever
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
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.company.VideoShowActivity
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import withTrigger


class ResumePreviewBackground : Fragment() {

    lateinit var backBtn : BackgroundBtn
    var imageUrl : String = ""
    var type : String = ""
    var relative : RelativeLayout? = null
    lateinit var video : VideoView
    lateinit var image : ImageView


    companion object {
        fun newInstance(url: String, type: String): ResumePreviewBackground {
            val fragment = ResumePreviewBackground()
            fragment.imageUrl = url
            fragment.type = type
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
                    if(imageUrl!=""){
                        relativeLayout {
                            if(type == "IMAGE"){
                                image = imageView {
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    adjustViewBounds = true
                                    maxHeight = dip(300)
                                }.lparams(wrapContent, dip(300)){
                                    centerInParent()
                                }
                                Glide.with(context)
                                    .asBitmap()
                                    .load(imageUrl)
                                    .placeholder(R.mipmap.no_network)
                                    .into(image)
                            }else{
                                val media = MediaMetadataRetriever()
                                media.setDataSource(imageUrl)
                                val bitmap = media.frameAtTime
                                image = imageView {
                                    imageBitmap = bitmap
                                }.lparams(wrapContent, wrapContent){
                                    centerInParent()
                                }
                            }
                        }.lparams(matchParent, dip(300))
                    }
                    image = imageView {
                        imageResource = R.mipmap.player
                        this.withTrigger().click {
                            backBtn.clickButton()
                        }
                    }.lparams(dip(70), dip(70)) {
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