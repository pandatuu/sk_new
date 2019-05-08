package com.example.sk_android.mvp.view.activity.myHelpFeedback

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class HowModifyPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.actionbar_bottom_border
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
                        text = "パスワードの変更方法は?"
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
                    height = dip(44)
                }
                relativeLayout {
                    verticalLayout {
                        textView {
                            text = "パスワードの変更方法は?"
                            textSize = 18f
                            textColor = Color.parseColor("#333333")
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            topMargin = dip(20)
                            bottomMargin = dip(18)
                            leftMargin = dip(15)
                        }

                        textView {
                            text = "「設置」画面に「パスワード変更」ボタンが"
                            textSize = 13f
                            textColor = Color.parseColor("#5C5C5C")
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(15)

                        }
                    }
                }.lparams{
                    width = matchParent
                    height = dip(82)
                }
                relativeLayout {
                    verticalLayout {
                        textView{
                            text = "未解决"
                            backgroundResource = R.drawable.button_shape
                            textColor = Color.parseColor("#02B8F7")
                            gravity = Gravity.CENTER
                            onClick {
                                toast("未解决")
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(47)
                            bottomMargin = dip(16)
                        }
                        textView{
                            text = "解決済み"
                            backgroundResource = R.drawable.button_shape_blue
                            gravity = Gravity.CENTER
                            onClick {
                                toast("解決済み")
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(47)
                        }
                    }.lparams{
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                        centerHorizontally()
                    }
                }.lparams{
                    width = matchParent
                    height = dip(110)
                    topMargin = dip(150)
                }
            }.lparams{
                width = matchParent
                height = matchParent
            }
        }
    }
}