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
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import java.util.*

class ResumeEditJob : Fragment() {

    companion object {
        fun newInstance(): ResumeEditJob {
            return ResumeEditJob()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return creatV()
    }

    fun creatV(): View {
        return UI {
            verticalLayout {
                //就職経験
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    verticalLayout {
                        relativeLayout {
                            textView {
                                text = "就職経験"
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
                            height = dip(60)
                        }
                        relativeLayout {
                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                relativeLayout {
                                    relativeLayout {
                                        textView {
                                            text = "ミラノ整形"
                                            textSize = 14f
                                            textColor = Color.parseColor("#FF202020")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                        }
                                        textView {
                                            text = "2018.03-1019.03"
                                            textSize = 12f
                                            textColor = Color.parseColor("#FF999999")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                            alignParentRight()
                                            rightMargin = dip(20)
                                        }
                                        toolbar {
                                            navigationIconResource = R.mipmap.icon_go_position
                                        }.lparams {
                                            width = dip(22)
                                            height = dip(22)
                                            alignParentRight()
                                        }
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        alignParentLeft()
                                        topMargin = dip(20)
                                    }
                                    textView {
                                        text = "UIデザイナー"
                                        textSize = 10f
                                        textColor = Color.parseColor("#FF999999")
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        topMargin = dip(40)
                                        alignParentLeft()
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    textView {
                                        text = "1、ソフト面における美術設計、クリエイティブ作業，及び 製作業務を担当する。"
                                        textSize = 12f
                                        textColor = Color.parseColor("#FF333333")
                                    }
                                    textView {
                                        text = "2、さまざまな関连ソフトのユーザーグループによると…"
                                        textSize = 12f
                                        textColor = Color.parseColor("#FF333333")
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    topMargin = dip(10)
                                }
                            }.lparams {
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(130)
                        }
                        relativeLayout {
                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                relativeLayout {
                                    relativeLayout {
                                        textView {
                                            text = "ミラノ整形"
                                            textSize = 14f
                                            textColor = Color.parseColor("#FF202020")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                        }
                                        textView {
                                            text = "2018.03-1019.03"
                                            textSize = 12f
                                            textColor = Color.parseColor("#FF999999")
                                        }.lparams {
                                            width = wrapContent
                                            height = wrapContent
                                            alignParentRight()
                                            rightMargin = dip(20)
                                        }
                                        toolbar {
                                            navigationIconResource = R.mipmap.icon_go_position
                                        }.lparams {
                                            width = dip(22)
                                            height = dip(22)
                                            alignParentRight()
                                        }
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        alignParentLeft()
                                        topMargin = dip(20)
                                    }
                                    textView {
                                        text = "UIデザイナー"
                                        textSize = 10f
                                        textColor = Color.parseColor("#FF999999")
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        topMargin = dip(40)
                                        alignParentLeft()
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    textView {
                                        text = "1、ソフト面における美術設計、クリエイティブ作業，及び 製作業務を担当する。"
                                        textSize = 12f
                                        textColor = Color.parseColor("#FF333333")
                                    }
                                    textView {
                                        text = "2、さまざまな関连ソフトのユーザーグループによると…"
                                        textSize = 12f
                                        textColor = Color.parseColor("#FF333333")
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    topMargin = dip(10)
                                }
                            }.lparams {
                                width = matchParent
                                height = matchParent
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(130)
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            relativeLayout {
                                backgroundResource = R.drawable.four_radius_grey_button
                                textView {
                                    text = "就職経験を追加する"
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
                            height = dip(80)
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = dip(400)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }
        }.view
    }
}