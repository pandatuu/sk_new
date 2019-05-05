package com.example.sk_android.mvp.view.activity

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import com.example.sk_android.R
import com.example.sk_android.custom.layout.recyclerView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.*

class SystemSetupActivity : AppCompatActivity() {

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
                        text = "設定"
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
                        //通知設定
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "通知設定"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //ご挨拶を編集
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "ご挨拶を編集"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //電話番号変更
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "電話番号変更"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //パスワード変更
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "パスワード変更"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //版本更新
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "版本更新"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            relativeLayout {
                                backgroundResource = R.drawable.new_icon
                                textView {
                                    text = "New"
                                    textSize =  10f
                                    textColor = Color.parseColor("#FFFFFF")
                                }.lparams{
                                    setMargins(dip(4),dip(1),dip(4),dip(1))
                                }
                            }.lparams{
                                width = dip(29)
                                height = dip(16)
                                leftMargin = dip(64)
                                centerVertically()
                            }
                            textView{
                                text = "バージョン1.0.1"
                                textColor = Color.parseColor("#B3B3B3")
                                textSize = 12f
                            }.lparams{
                                alignParentRight()
                                centerVertically()
                                rightMargin = dip(16)
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        //私たちについて
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "私たちについて"
                                textSize = 13f
                                textColor = Color.parseColor("#5C5C5C")
                            }.lparams{
                                alignParentLeft()
                                centerVertically()
                            }
                            toolbar {
                                backgroundResource = R.mipmap.icon_go_position
                            }.lparams{
                                alignParentRight()
                                width = dip(6)
                                height = dip(11)
                                centerVertically()
                            }
                        }.lparams{
                            width = matchParent
                            height = dip(55)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
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