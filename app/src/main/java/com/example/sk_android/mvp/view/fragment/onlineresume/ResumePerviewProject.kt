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
import com.example.sk_android.R
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.onlineresume.projectexprience.ProjectExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import java.text.SimpleDateFormat
import java.util.*

class ResumePerviewProject : Fragment() {

    private lateinit var linea: LinearLayout

    companion object {
        var myResult: ArrayList<ProjectExperienceModel> = arrayListOf()
        fun newInstance(): ResumePerviewProject {
            return ResumePerviewProject()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return creatV()
    }

    @SuppressLint("SetTextI18n", "RtlHardcoded")
    fun creatV(): View {
        val view = UI {
            verticalLayout {
                //プロジェクト経験
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    verticalLayout {
                        relativeLayout {
                            textView {
                                text = "プロジェクト経験"
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
                            height = dip(60)
                        }
                        linea = linearLayout {
                            orientation = LinearLayout.VERTICAL
                        }.lparams(matchParent, wrapContent)
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

    @SuppressLint("RtlHardcoded", "SetTextI18n")
    fun initView(from: Int) {
        if (from == 1) {
            val application: App? = App.getInstance()
            application?.setResumePerviewProject(this)
        }
        if (myResult.size == 0) {
            //第一次进入
        } else {
            println("=====project=====")
            println(myResult)
            println(from)
            println("=====project=====")
            linea.removeAllViews()
            for (item in myResult) {
                val childView = UI {
                    linearLayout {
                        relativeLayout {
                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                relativeLayout {
                                    linearLayout {
                                        orientation = LinearLayout.HORIZONTAL
                                        textView {
                                            //                                                    text = if(item.projectName.length>11) "${item.projectName.substring(0,11)}..." else item.projectName
                                            text = item.projectName
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
                                            text = "${longToString(item.startDate)} - ${longToString(item.endDate)}"
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
                                        text = item.position
                                        textSize = 10f
                                        textColor = Color.parseColor("#FF999999")
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        topMargin = dip(20)
                                    }
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    textView {
                                        text = item.responsibility
                                        ellipsize = TextUtils.TruncateAt.END
                                        maxLines = 3
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
                            height = wrapContent
                            bottomMargin = dip(10)
                        }
                    }
                }.view
                linea.addView(childView)
            }
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
        application!!.setResumePerviewJob(null)
    }
}