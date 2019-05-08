package com.example.sk_android.mvp.view.activity.myHelpFeedback

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.design.navigationView

class MyFeedbackContentActivity : AppCompatActivity() {

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
                            textView {
                                text="内容"
                                textSize = 18f
                            }.lparams{
                                alignParentLeft()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(25)
                            topMargin = dip(20)
                        }
                        relativeLayout {
                            verticalLayout {
                                imageView {
                                    setImageResource(R.drawable.qipao)
                                }.lparams {
                                    width = wrapContent
                                    height = dip(11)
                                    leftMargin = dip(10)
                                    topPadding = dip(-5)
                                }
                                relativeLayout {
                                    backgroundResource = R.drawable.qipao_border
                                    textView {
                                        text =
                                            "投票機能が欲しい"
                                        textSize = 14f
                                        gravity = Gravity.CENTER_VERTICAL
                                    }.lparams {
                                        width = matchParent
                                        height = matchParent
                                        leftMargin = dip(10)
                                        rightMargin = dip(8)
                                    }
                                }.lparams{
                                    width = matchParent
                                    height = matchParent
                                }
                            }.lparams{
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(61)
                        }
                        relativeLayout {
                            textView {
                                text="返事"
                                textSize = 18f

                            }.lparams{
                                alignParentLeft()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(25)
                            topMargin = dip(20)
                        }
                        relativeLayout {
                            verticalLayout {
                                imageView {
                                    setImageResource(R.drawable.qipao)
                                }.lparams {
                                    width = wrapContent
                                    height = dip(11)
                                    leftMargin = dip(10)
                                    topPadding = dip(-5)
                                }
                                relativeLayout {
                                    imageView {
                                        setImageResource(R.drawable.qipao_border)
                                    }.lparams {
                                        width = matchParent
                                        height = matchParent
                                    }
                                    textView {
                                        text =
                                            "私达はすでにあなたのフィードバックと提案を受け 取って、あなたのsk体験を引き続きフィードバック することができて、あなたのフィードバックと提案 の内容は完全に秘密にして、最后にあなたと连络を 取ります"
                                        textSize = 14f
                                        gravity = Gravity.CENTER_VERTICAL
                                    }.lparams {
                                        width = matchParent
                                        height = matchParent
                                        leftMargin = dip(10)
                                        rightMargin = dip(8)
                                    }
                                }.lparams{
                                    width = matchParent
                                    height = matchParent
                                }
                            }.lparams{
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(146)
                        }
                    }.lparams{
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}