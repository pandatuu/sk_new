package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
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
import com.example.sk_android.mvp.application.App
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger


class ResumeEditBackground : Fragment() {

    lateinit var backBtn: BackgroundBtn
    lateinit var video: VideoView
    lateinit var image: ImageView


    companion object {
        var myResult: String = ""
        fun newInstance(): ResumeEditBackground {
            return ResumeEditBackground()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        backBtn = activity as BackgroundBtn

        return createView()
    }

    private fun createView(): View? {
        val view = UI {
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
                    }.lparams(wrapContent, wrapContent) {
                        alignParentRight()
                    }
                    relativeLayout {
                        image = imageView {
                            scaleType = ImageView.ScaleType.CENTER_CROP
                            adjustViewBounds = true
                            maxHeight = dip(300)
                        }.lparams(wrapContent, dip(300)) {
                            centerInParent()
                        }
                    }.lparams(matchParent, dip(300))
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
        initView(1)
        return view
    }

    fun initView(from: Int) {
        if (from == 1) {
            val application: App? = App.getInstance()
            application?.setResumeEditBackground(this)
        }
        if (myResult == "") {
            //第一次进入
        } else {
            if(myResult.isNotEmpty()){
                Glide.with(this.context!!)
                    .asBitmap()
                    .load(myResult)
                    .placeholder(R.mipmap.no_network)
                    .into(image)
            }
        }
    }

    interface BackgroundBtn {
        fun clickButton()
        fun jumpExample()
    }

    override fun onDestroy() {
        super.onDestroy()

        val application: App? = App.getInstance()
        application!!.setResumeEditBackground(null)
    }
}