package com.example.sk_android.mvp.view.activity.myhelpfeedback

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import com.example.sk_android.R
import org.jetbrains.anko.*

class JobSearchStrategyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        relativeLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
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
                        text = "求職攻略"
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
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "チュートリアル"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#FF333333")
                                textSize = 13f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                alignParentLeft()
                                centerInParent()
                            }
                            toolbar {
                                backgroundResource = R.color.transparent
                                isEnabled = true
                                title = ""
                                navigationIconResource = R.mipmap.icon_go_position
                            }.lparams {
                                width = dip(16)
                                height = wrapContent
                                alignParentRight()
                                centerInParent()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(55)
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "攻略"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#FF333333")
                                textSize = 13f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                alignParentLeft()
                                centerInParent()
                            }
                            toolbar {
                                backgroundResource = R.color.transparent
                                isEnabled = true
                                title = ""
                                navigationIconResource = R.mipmap.icon_go_position
                            }.lparams {
                                width = dip(16)
                                height = wrapContent
                                alignParentRight()
                                centerInParent()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(55)
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "アクティビティ"
                                backgroundColor = Color.TRANSPARENT
                                gravity = Gravity.CENTER
                                textColor = Color.parseColor("#FF333333")
                                textSize = 13f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                alignParentLeft()
                                centerInParent()
                            }
                            toolbar {
                                backgroundResource = R.color.transparent
                                isEnabled = true
                                title = ""
                                navigationIconResource = R.mipmap.icon_go_position
                            }.lparams {
                                width = dip(16)
                                height = wrapContent
                                alignParentRight()
                                centerInParent()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(55)
                            leftPadding = dip(15)
                            rightPadding = dip(15)
                        }
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }.lparams{
                width = matchParent
                height = matchParent
                backgroundColor = Color.WHITE
            }
        }
    }
}