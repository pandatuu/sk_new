package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MyFeedbackContentActivity : AppCompatActivity() {

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
                        onClick {
                            finish()
                        }
                    }.lparams{
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "私のフィードバック"
                        backgroundColor = Color.TRANSPARENT
                        gravity = Gravity.CENTER
                        textColor = Color.parseColor("#FF333333")
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
                                    height = dip(16)
                                    leftMargin = dip(5)
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
                                    height = dip(16)
                                    leftMargin = dip(5)
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