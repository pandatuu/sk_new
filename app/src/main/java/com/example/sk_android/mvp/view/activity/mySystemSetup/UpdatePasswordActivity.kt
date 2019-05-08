package com.example.sk_android.mvp.view.activity.mySystemSetup

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*

class UpdatePasswordActivity : AppCompatActivity() {

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
                        text = "パスワードを設置する"
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
                            textView {
                                text = "現在のパスワード    "
                                textColor = Color.parseColor("#202020")
                                textSize = 12f
                            }.lparams{
                                alignParentLeft()
                                leftMargin = dip(10)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(5)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.shadow_border_box
                            relativeLayout {
                                editText {
                                    hint = "現在のパスワードを入力してください"
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    textSize = 15f
                                    background = null
                                }.lparams{
                                    width = matchParent
                                    height = wrapContent
                                }
                            }.lparams{
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        relativeLayout {
                            textView {
                                text = "新しいパスワード"
                                textColor = Color.parseColor("#202020")
                                textSize = 12f
                            }.lparams{
                                alignParentLeft()
                                leftMargin = dip(10)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(5)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.shadow_border_box
                            relativeLayout {
                                editText {
                                    hint = "6-20桁の数字/アルバイト"
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    textSize = 15f
                                    background = null
                                }.lparams{
                                    width = matchParent
                                    height = wrapContent
                                }
                            }.lparams{
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        relativeLayout {
                            textView {
                                text = "新しいパスワード（確認用）"
                                textColor = Color.parseColor("#202020")
                                textSize = 12f
                            }.lparams{
                                alignParentLeft()
                                leftMargin = dip(10)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(5)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.shadow_border_box
                            relativeLayout {
                                editText {
                                    hint = "もう一度入力してください"
                                    textSize = 15f
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    background = null
                                }.lparams{
                                    width = matchParent
                                    height = wrapContent
                                }
                            }.lparams{
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.fourdp_blue_button
                            textView {
                                text = "パスワードを設置する"
                                textSize = 16f
                                textColor = Color.WHITE
                            }.lparams{
                                width = dip(148)
                                height = dip(23)
                                centerInParent()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(47)
                            topMargin = dip(45)
                        }
                    }.lparams{
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }.lparams{
                    width = matchParent
                    height = matchParent
                    topMargin = dip(64)
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}