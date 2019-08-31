package com.example.sk_android.mvp.view.fragment.onlineresume

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import click
import com.example.sk_android.R
import com.example.sk_android.mvp.application.App
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

    private lateinit var eduFrag: EduFrag
    private lateinit var resumeBasic: ResumeEditBasic
    private lateinit var linea: LinearLayout

    private val edu = mapOf(
        "MIDDLE_SCHOOL" to "中卒",
        "HIGH_SCHOOL" to "高卒",
        "SHORT_TERM_COLLEGE" to "専門卒・短大卒",
        "BACHELOR" to "大卒",
        "MASTER" to "修士",
        "DOCTOR" to "博士"
    )

    companion object {
        var myResult: ArrayList<EduExperienceModel> = arrayListOf()
        fun newInstance(basic: ResumeEditBasic): ResumeEditEdu {
            val frag = ResumeEditEdu()
            frag.resumeBasic = basic
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        eduFrag = activity as EduFrag
        return creatV()
    }

    @SuppressLint("SetTextI18n", "RtlHardcoded")
    fun creatV(): View {
        val view = UI {
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
                                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
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
                        linea = linearLayout {
                            orientation = LinearLayout.VERTICAL
                        }.lparams(matchParent, wrapContent)
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
        initView(1)
        return view
    }

    fun initView(from: Int) {
        if (from == 1) {
            val application: App? = App.getInstance()
            application?.setResumeEditEdu(this)
        }
        if ((myResult.size == 0)) {
            //第一次进入
        } else {
            println("=====edu=====")
            println(myResult)
            println(from)
            println("=====edu=====")
            linea.removeAllViews()
            for (item in myResult) {
                val childView = UI {
                    linearLayout {
                        relativeLayout {
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                textView {
                                    //                                                    text = if(item.schoolName.length>11) "${item.schoolName.substring(0,11)}..." else item.schoolName
                                    text = item.schoolName
                                    ellipsize = TextUtils.TruncateAt.END
                                    maxLines = 1
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF202020")
                                }.lparams {
                                    width = 0
                                    weight = 1f
                                    height = wrapContent
                                }
                                textView {
                                    text =
                                        "${longToString(item.startDate)} - ${longToString(item.endDate)}"
                                    textSize = 12f
                                    textColor = Color.parseColor("#FF999999")
                                }.lparams {
                                    width = 0
                                    weight = 1f
                                    height = wrapContent
                                    gravity = Gravity.RIGHT
                                    leftMargin = dip(20)
                                    rightMargin = dip(20)
                                }
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                alignParentLeft()
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
                            this.withTrigger().click {
                                eduFrag.eduClick(item.id.toString())
                            }
                        }.lparams {
                            width = matchParent
                            height = wrapContent
                            bottomMargin = dip(15)
                        }
                    }
                }.view
                linea.addView(childView)
            }
            resumeBasic.setBackground(myResult[0].educationalBackground)


        }
    }

    // 类型转换
    @SuppressLint("SimpleDateFormat")
    private fun longToString(long: Long): String {
        return SimpleDateFormat("yyyy/MM/dd").format(Date(long))
    }

    override fun onDestroy() {
        super.onDestroy()

        val application: App? = App.getInstance()
        application!!.setResumeEditEdu(null)
    }
}