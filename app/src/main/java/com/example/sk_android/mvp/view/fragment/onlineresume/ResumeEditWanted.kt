package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import java.util.*

class ResumeEditWanted : Fragment() {

    companion object {
        fun newInstance(): ResumeEditWanted {
            return ResumeEditWanted()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return creatV()
    }

    fun creatV(): View {
        return UI {
            verticalLayout {
                //就職状況
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    textView {
                        text = "就職状況"
                        textSize = 16f
                        textColor = Color.parseColor("#FF202020")
                        setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerVertically()
                        alignParentLeft()
                    }
                    textView {
                        text = "職場に勤め、チャンスを考える"
                        textSize = 13f
                        textColor = Color.parseColor("#FF5C5C5C")
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        centerVertically()
                        alignParentRight()
                        rightMargin = dip(15)
                    }
                    toolbar {
                        navigationIconResource = R.mipmap.icon_go_position
                    }.lparams {
                        width = dip(20)
                        height = dip(20)
                        centerVertically()
                        alignParentRight()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(80)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
                //希望の業種
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    verticalLayout {
                        relativeLayout {
                            textView {
                                text = "希望の業種"
                                textSize = 16f
                                textColor = Color.parseColor("#FF202020")
                                setTypeface(Typeface.defaultFromStyle(Typeface.BOLD))
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerVertically()
                                alignParentLeft()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(65)
                        }
                        relativeLayout {
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                textView {
                                    text = "PHP開発エンジニア"
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF202020")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                }
                                textView {
                                    text = "30万-60万"
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF202020")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    leftMargin = dip(10)
                                }
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                alignParentLeft()
                                topMargin = dip(20)
                                alignParentTop()
                            }
                            textView {
                                text = "東京都 IT ソフトウェアエンジニア"
                                textSize = 10f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(40)
                                alignParentLeft()
                            }
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                            }.lparams {
                                width = dip(22)
                                height = dip(22)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(65)
                        }
                        relativeLayout {
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                textView {
                                    text = "視覚デザイン"
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF202020")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                }
                                textView {
                                    text = "30万-60万"
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF202020")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    leftMargin = dip(10)
                                }
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                alignParentLeft()
                                topMargin = dip(20)
                                alignParentTop()
                            }
                            textView {
                                text = "東京都 IT ソフトウェアエンジニア"
                                textSize = 10f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(40)
                                alignParentLeft()
                            }
                            toolbar {
                                navigationIconResource = R.mipmap.icon_go_position
                            }.lparams {
                                width = dip(22)
                                height = dip(22)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(65)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            relativeLayout {
                                backgroundResource = R.drawable.four_radius_grey_button
                                textView {
                                    text = "就職希望を追加する"
                                    textSize = 16f
                                    textColor = Color.parseColor("#FF202020")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    centerInParent()
                                }
                            }.lparams {
                                width = matchParent
                                height = dip(50)
                                centerInParent()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(85)
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = dip(280)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }
        }.view
    }
}