package com.example.sk_android.mvp.view.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*

class MyFeedbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
                    verticalLayout {
                        relativeLayout {
                            backgroundResource = R.drawable.actionbar_bottom_border
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
                                text = "私のフィードバック"
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
                                text = "投票機能が欲しい"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#5C5C5C")
                                textSize = 13f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams{
                                alignParentLeft()
                                centerInParent()
                            }
                            textView {
                                text = "返事済み"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#B3B3B3")
                                textSize = 12f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams{
                                alignParentRight()
                                centerInParent()
                                rightMargin = dip(16)
                            }
                            toolbar {
                                backgroundResource = R.color.transparent
                                isEnabled = true
                                title = ""
                                navigationIconResource = R.mipmap.icon_go_position
                            }.lparams{
                                width = dip(6)
                                height = dip(11)
                                alignParentRight()
                                centerInParent()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "設置中にセキュリティモジュールを搭載…"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#5C5C5C")
                                textSize = 13f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams{
                                alignParentLeft()
                                centerInParent()
                            }
                            textView {
                                text = "未返事"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#B3B3B3")
                                textSize = 12f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams{
                                alignParentRight()
                                centerInParent()
                                rightMargin = dip(16)
                            }
                            toolbar {
                                backgroundResource = R.color.transparent
                                isEnabled = true
                                title = ""
                                navigationIconResource = R.mipmap.icon_go_position
                            }.lparams{
                                width = dip(6)
                                height = dip(11)
                                alignParentRight()
                                centerInParent()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                        }
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