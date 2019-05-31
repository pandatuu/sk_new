package com.example.sk_android.mvp.view.activity.videointerview

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import com.example.sk_android.R
import org.jetbrains.anko.*

class SeeOfferOfRefuse : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        frameLayout {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.title_bottom_border
                    toolbar {
                        isEnabled = true
                        title = ""
                        navigationIconResource = R.mipmap.icon_back
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        alignParentLeft()
                        centerVertically()
                    }

                    textView {
                        text = "offer詳細を見る"
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

                verticalLayout {
                    backgroundResource = R.mipmap.shading
                        verticalLayout {
                            textView {
                                text = "聘用通知书/offer"
                                textSize = 16f
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }
                            textView {
                                text = "拝啓"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text =
                                    "このたびは、弊社の求人にご応募いただきましてありがとうございました。また、先日はお忙しい中をご足労いただきましたこと、重ねてお礼申し上げます。厳正なる選考の結果、貴殿を採用することに決定いたしましたのでご通知申し上げます。"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text =
                                    "つきましては、同封の書類を良くお読みいただき、必要事項をご記入の上、入社日にご持参下さいますようお願い申し上げます。なお、応募書類は当社人事部にてお預かりさせていただきますのでご了承ください。今後とも宜しくお願い申し上げます。"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "敬具"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "1．提出書類入社承諾書"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "誓約書"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "身元保証書"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            textView {
                                text = "2．入社日令和○○年○○月○○日（○曜日） 朝○時○分までに○Ｆ人事部におこしください"
                                textSize = 14f
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                        }.lparams{
                        setMargins(dip(32), dip(110), dip(32), 0)
                        width = matchParent
                        height = wrapContent
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