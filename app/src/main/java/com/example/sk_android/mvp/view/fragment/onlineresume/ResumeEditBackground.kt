package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Intent
import android.graphics.Color
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.VideoView
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.view.activity.onlineresume.ShowExample
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger


class ResumeEditBackground : Fragment() {

    lateinit var backBtn: BackgroundBtn
    var imageUrl: String = ""
    var type: String = ""
    lateinit var video: VideoView
    lateinit var image: ImageView


    companion object {
        fun newInstance(url: String, type: String): ResumeEditBackground {
            val fragment = ResumeEditBackground()
            fragment.imageUrl = url
            fragment.type = type
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        backBtn = activity as BackgroundBtn

        return createView()
    }

    private fun createView(): View? {
        return UI {
            relativeLayout {
                relativeLayout {
                    backgroundResource = R.mipmap.job_photo_upload
                    frameLayout {
                        textView {
                            text = "手本を見る"
                            textColor = Color.parseColor("#FFFFB706")
                            textSize = 15f
                            withTrigger().click {
                                backBtn.jumpExample()
                            }
                        }.lparams(wrapContent, wrapContent) {
                            rightMargin = dip(15)
                            topMargin = dip(27)
                        }
                    }.lparams(wrapContent, wrapContent){
                        alignParentRight()
                    }
                    if (imageUrl != "") {
                        relativeLayout {
                            if (type == "IMAGE") {
                                image = imageView {
                                    scaleType = ImageView.ScaleType.CENTER_CROP
                                    adjustViewBounds = true
                                    maxHeight = dip(300)
                                }.lparams(wrapContent, dip(300)) {
                                    centerInParent()
                                }
                                Glide.with(context)
                                    .asBitmap()
                                    .load(imageUrl)
                                    .placeholder(R.mipmap.no_network)
                                    .into(image)
                            } else {
                                val media = MediaMetadataRetriever()
                                media.setDataSource(imageUrl)
                                val bitmap = media.frameAtTime
                                image = imageView {
                                    imageBitmap = bitmap
                                }.lparams(wrapContent, wrapContent) {
                                    centerInParent()
                                }
                            }
                        }.lparams(matchParent, dip(300))
                    }
                    button {
                        backgroundResource = R.drawable.fifteen_radius_button
                        text = " ビデオをアップロード"
                        textSize = 10f
                        textColor = Color.WHITE
                        withTrigger().click {
                            backBtn.clickButton()
                        }
                    }.lparams {
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
    }

    interface BackgroundBtn {
        fun clickButton()
        fun jumpExample()
    }
}