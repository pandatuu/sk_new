package com.example.sk_android.mvp.view.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*

class BindPhoneNumberActivity : AppCompatActivity() {

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
                        text = "電話番号変更"
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
                            backgroundResource = R.drawable.shadow_border_box
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
                            backgroundResource = R.drawable.shadow_border_box
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
                                alignParentLeft()
                                centerVertically()
                            }
                            relativeLayout {
                                backgroundResource = R.drawable.list_button
                                textView {
                                    text = "検証コードを取得"
                                    textColor = Color.parseColor("#5C5C5C")
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
                            bottomMargin = dip(30)
                        }
                        relativeLayout {
                            textView {
                                text = "検証コードの受信が出来なかった？"
                                textSize = 12f
                                textColor = Color.parseColor("#999999")
                            }.lparams{
                                width = dip(198)
                                height = dip(17)
                                centerHorizontally()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(10)
                        }
                        relativeLayout {
                            textView {
                                backgroundResource = R.drawable.bottom_black_line
                                text = "音声で検証コードを取得"
                                textSize = 12f
                                textColor = Color.parseColor("#999999")
                            }.lparams{
                                width = dip(137)
                                height = dip(17)
                                centerHorizontally()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(17)
                            bottomMargin = dip(45)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.fourdp_blue_button
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
                    topMargin = dip(85)
                }
            }.lparams {
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}