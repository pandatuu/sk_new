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
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI

class ResumeEditEdu : Fragment() {

    interface EduFrag{
        fun eduCLick(eduId: String)
    }

    private var mLIst: MutableList<EduExperienceModel>? = null
    private lateinit var eduFrag : EduFrag

    companion object {
        fun newInstance(list: MutableList<EduExperienceModel>?): ResumeEditEdu {
            var frag = ResumeEditEdu()
            frag.mLIst = list
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        eduFrag = activity as EduFrag
        return creatV()
    }

    fun creatV(): View {
        return UI {
            verticalLayout {
                //教育経験
                relativeLayout {
                    backgroundResource = R.drawable.twenty_three_radius_bottom
                    verticalLayout {
                        relativeLayout {
                            textView {
                                text = "教育経験"
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
                            height = dip(50)
                        }
                        relativeLayout {
                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                relativeLayout {
                                    if (mLIst != null) {
                                        for (item in mLIst!!) {
                                            relativeLayout {
                                                textView {
                                                    text = item.schoolName
                                                    textSize = 14f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    centerVertically()
                                                }
                                                textView {
                                                    text = "2017.03-2017.06"
                                                    textSize = 12f
                                                    textColor = Color.parseColor("#FF999999")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    alignParentRight()
                                                    rightMargin = dip(25)
                                                    centerVertically()
                                                }
                                                toolbar {
                                                    navigationIconResource = R.mipmap.icon_go_position
                                                    onClick{
                                                        eduFrag.eduCLick(item.id.toString())
                                                    }
                                                }.lparams {
                                                    width = dip(20)
                                                    height = dip(20)
                                                    alignParentRight()
                                                    centerVertically()
                                                }
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                topMargin = dip(20)
                                            }
                                            textView {
                                                text = item.educationalBackground.toString()
                                                textSize = 10f
                                                textColor = Color.parseColor("#FF999999")
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                topMargin = dip(40)
                                            }
                                        }
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                }
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                        }
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            relativeLayout {
                                backgroundResource = R.drawable.four_radius_grey_button
                                textView {
                                    text = "教育経験を追加する"
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
                    height = dip(200)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }
        }.view
    }
}