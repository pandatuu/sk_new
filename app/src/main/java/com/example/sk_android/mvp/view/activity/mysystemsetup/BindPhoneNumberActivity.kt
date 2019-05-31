package com.example.sk_android.mvp.view.activity.mysystemsetup

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import com.umeng.message.PushAgent
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class BindPhoneNumberActivity : AppCompatActivity() {

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
                        text = "電話番号変更"
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

                relativeLayout {
                    verticalLayout {
                        relativeLayout {
                            backgroundResource = R.drawable.input_box
                            relativeLayout {
                                textView {
                                    text = "+86"
                                    textSize = 15f
                                    textColor = Color.parseColor("#202020")
                                }.lparams{
                                    width = dip(28)
                                    height = dip(21)
                                    leftMargin = dip(10)
                                    centerVertically()
                                }
                                toolbar {
                                    navigationIconResource =R.mipmap.icon_go_position
                                }.lparams{
                                    width = dip(6)
                                    height = dip(11)
                                    alignParentRight()
                                    centerVertically()
                                }
                            }.lparams{
                                width = dip(51)
                                height = matchParent
                                alignParentLeft()
                            }
                            relativeLayout {
                                editText {
                                    hint = "電話番号を入力してください"
                                    textSize = 15f
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    background = null
                                }.lparams{
                                    width = matchParent
                                    height = wrapContent
                                }
                            }.lparams{
                                width = dip(212)
                                height = matchParent
                                leftMargin = dip(64)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(15)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.input_box
                            relativeLayout {
                                editText {
                                    hint = "検証コードを入力してください"
                                    textSize = 14f
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    background = null
                                }.lparams{
                                    width = wrapContent
                                    height = wrapContent
                                }
                            }.lparams{
                                width = dip(215)
                                height = wrapContent
                                leftMargin = dip(5)
                                alignParentLeft()
                                centerVertically()
                            }
                            relativeLayout {
                                backgroundResource = R.drawable.list_button
                                textView {
                                    text = "検証コードを取得"
                                    textColor = Color.parseColor("#FF5C5C5C")
                                    textSize = 12f
                                }.lparams{
                                    centerInParent()
                                }
                            }.lparams{
                                centerVertically()
                                width = dip(103)
                                height = dip(27)
                                alignParentRight()
                                rightMargin = dip(5)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(44)
                            bottomMargin = dip(55)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.button_shape_orange
                            textView {
                                text = "電話番号を設置する"
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
                            bottomMargin = dip(10)
                        }
                        relativeLayout {
                            textView {
                                text = "検証コードの受信が出来なかった？"
                                textSize = 12f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams{
                                width = dip(198)
                                height = dip(17)
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(17)
                        }

                    }.lparams{
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
                    }
                }.lparams{
                    width = matchParent
                    height = dip(269)
                    topMargin = dip(45)
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}