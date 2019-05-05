package com.example.sk_android.mvp.view.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*


class NotificationSettingsActivity : AppCompatActivity()  {

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
                        text = "通知設定"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.BLACK
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
                }

                relativeLayout {
                    verticalLayout {
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "音声と振動"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                centerVertically()
                                alignParentLeft()
                            }

                            switch {
                                setThumbResource(R.drawable.thumb)
                                setTrackResource(R.drawable.track)
                                isChecked = true
                            }.lparams{
                                alignParentRight()
                                centerVertically()
                                rightMargin = dip(15)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            setMargins(dip(15),0,0,0)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "大切なメッセージが受信できなかった場合、SMS で通知する"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                width = dip(299)
                                centerVertically()
                                alignParentLeft()
                            }
                            switch {
                                setThumbResource(R.drawable.thumb)
                                setTrackResource(R.drawable.track)
                            }.lparams{
                                alignParentRight()
                                centerVertically()
                                rightMargin = dip(15)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(74)
                            setMargins(dip(15),0,0,0)
                        }
                    }.lparams{
                        width = matchParent
                        height = matchParent
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}