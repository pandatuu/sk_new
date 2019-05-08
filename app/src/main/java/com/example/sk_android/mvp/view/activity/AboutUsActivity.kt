package com.example.sk_android.mvp.view.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*

class AboutUsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    toolbar {
                        backgroundResource = R.color.transparent
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams {
                        width = dip(9)
                        height = dip(15)
                        alignParentBottom()
                        leftMargin = dip(15)
                        bottomMargin =  dip(11)
                    }

                    textView {
                        text = "私たちについて"
                        gravity = Gravity.CENTER
                        textColor = Color.parseColor("#333333")
                        textSize = 16f
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        width = dip(147)
                        height = dip(23)
                        alignParentBottom()
                        centerHorizontally()
                        bottomMargin =  dip(11)
                    }
                }.lparams{
                    width = matchParent
                    height = dip(64)
                    backgroundColor = Color.WHITE
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