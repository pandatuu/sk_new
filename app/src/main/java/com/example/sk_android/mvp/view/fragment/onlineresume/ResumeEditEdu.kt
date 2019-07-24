package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger
import java.text.SimpleDateFormat
import java.util.*

class ResumeEditEdu : Fragment() {

    interface EduFrag {
        fun eduClick(eduId: String)
        fun addEduClick()
    }

    private var mLIst: MutableList<EduExperienceModel>? = null
    private lateinit var eduFrag: EduFrag

    val edu = mapOf(
        "MIDDLE_SCHOOL" to "中学卒業及び以下",
        "HIGH_SCHOOL" to "高卒",
        "SHORT_TERM_COLLEGE" to "専門卒・短大卒",
        "BACHELOR" to "大卒",
        "MASTER" to "修士",
        "DOCTOR" to "博士"
    )

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
                                if (mLIst != null) {
                                    for (item in mLIst!!) {
                                        relativeLayout {
                                            relativeLayout {
                                                textView {
                                                    text = if(item.schoolName.length>11) "${item.schoolName.substring(0,11)}..." else item.schoolName
                                                    textSize = 14f
                                                    textColor = Color.parseColor("#FF202020")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    centerVertically()
                                                }
                                                textView {
                                                    text =
                                                        "${longToString(item.startDate)} - ${longToString(item.endDate)}"
                                                    textSize = 12f
                                                    textColor = Color.parseColor("#FF999999")
                                                }.lparams {
                                                    width = wrapContent
                                                    height = wrapContent
                                                    alignParentRight()
                                                    rightMargin = dip(25)
                                                    centerVertically()
                                                }
                                                imageView {
                                                    imageResource = R.mipmap.icon_go_position
                                                    this.withTrigger().click {
                                                        eduFrag.eduClick(item.id.toString())
                                                    }
                                                }.lparams {
                                                    width = dip(6)
                                                    height = dip(11)
                                                    alignParentRight()
                                                    centerVertically()
                                                }
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                            }
                                            textView {
                                                text = edu[item.educationalBackground.toString()]
                                                textSize = 10f
                                                textColor = Color.parseColor("#FF999999")
                                            }.lparams {
                                                width = wrapContent
                                                height = wrapContent
                                                topMargin = dip(20)
                                            }
                                            this.withTrigger().click {
                                                eduFrag.eduClick(item.id.toString())
                                            }
                                        }.lparams {
                                            width = matchParent
                                            height = wrapContent
                                            bottomMargin = dip(15)
                                        }
                                    }
                                } else {
                                    relativeLayout {
                                        padding = dip(10)
                                        val image = imageView {}.lparams(dip(50), dip(60)) { centerInParent() }
                                        Glide.with(this@relativeLayout)
                                            .load(R.mipmap.turn_around)
                                            .into(image)
                                    }.lparams {
                                        width = matchParent
                                        height = dip(60)
                                        bottomMargin = dip(20)
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
                                this.withTrigger().click {
                                    eduFrag.addEduClick()
                                }
                            }.lparams {
                                width = matchParent
                                height = dip(50)
                                gravity = Gravity.TOP
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(65)
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