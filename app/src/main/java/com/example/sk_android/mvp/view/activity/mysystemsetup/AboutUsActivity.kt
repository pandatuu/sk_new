package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.umeng.message.PushAgent
import org.jetbrains.anko.*

class AboutUsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PushAgent.getInstance(this).onAppStart();

        relativeLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "私たちについて"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerInParent()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(54)
                }

                verticalLayout {
                    relativeLayout {
                        imageView {
                            backgroundResource = R.mipmap.sk
                        }.lparams {
                            width = wrapContent
                            height = matchParent
                            centerHorizontally()
                        }
                    }.lparams{
                        width = matchParent
                        height = dip(110)
                        topMargin = dip(90)
                    }
                    relativeLayout {
                        textView {
                            text = "SK logo"
                            textSize = 24f
                            textColor = Color.parseColor("#333333")
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            centerHorizontally()
                        }
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(9)
                    }
                    relativeLayout{
                        textView {
                            text = "版本：v1.0.1"
                            textSize = 14f
                            textColor = Color.parseColor("#333333")
                        }.lparams{
                            width = wrapContent
                            height = wrapContent
                            centerHorizontally()
                        }
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        topMargin = dip(14)
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                    backgroundColor = Color.TRANSPARENT
                }

            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.TRANSPARENT
            }
        }
    }
}