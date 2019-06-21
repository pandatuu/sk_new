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
import java.text.SimpleDateFormat
import java.util.*

class ResumePerviewEdu : Fragment() {


    private var mLIst: MutableList<EduExperienceModel>? = null

    companion object {
        fun newInstance(list: MutableList<EduExperienceModel>?): ResumePerviewEdu {
            var frag = ResumePerviewEdu()
            frag.mLIst = list
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
                                                    text = "${longToString(item.startDate)} - ${longToString(item.endDate)}"
                                                    textSize = 12f
                                                    textColor = Color.parseColor("#FF999999")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    alignParentRight()
                                                    rightMargin = dip(25)
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
                                    bottomMargin = dip(20)
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
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = wrapContent
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
            }
        }.view
    }

    // 类型转换
    private fun longToString(long: Long): String {
        val str = SimpleDateFormat("yyyy/MM/dd").format(Date(long))
        return str
    }
}