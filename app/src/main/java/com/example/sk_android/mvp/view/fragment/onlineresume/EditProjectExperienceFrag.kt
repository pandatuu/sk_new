package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.projectexprience.ProjectExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.toast
import java.text.SimpleDateFormat
import java.util.*


class EditProjectExperienceFrag : Fragment() {

    interface EditProject {
        fun startDate()
        fun endDate()
    }

    private var addJobEx: ProjectExperienceModel? = null
    private lateinit var editproject: EditProject

    private lateinit var projectName: EditText //项目名字
    private lateinit var position: EditText //项目中的职位
    private lateinit var startDate: TextView //开始日期
    private lateinit var endDate: TextView //结束日期
    private lateinit var projectUrl: EditText //项目链接
    private lateinit var primaryJob: EditText //项目介绍

    companion object {
        fun newInstance(context: Context): EditProjectExperienceFrag {
            return EditProjectExperienceFrag()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        editproject = activity as EditProject
        return createView()
    }

    fun setProjectExperience(obj: ProjectExperienceModel) {
        projectName.text = SpannableStringBuilder(obj.projectName)
        position.text = SpannableStringBuilder(obj.position)
        startDate.text = longToString(obj.startDate)
        endDate.text = longToString(obj.endDate)
        primaryJob.text = SpannableStringBuilder(obj.responsibility)
        projectUrl.text = SpannableStringBuilder(obj.attributes.projectUrl)
    }

    fun getProjectExperience(): Map<String, Any>? {

        //验证项目名字字符长度 2-30
        val nLength = projectName.text.length
        if (!(nLength in 1..30)) {
            toast("项目名字长度应为2-30")
            return null
        }

        //验证项目中的职位字符长度 2-30
        val pLength = position.text.length
        if (!(pLength in 1..30)) {
            toast("项目中的职位长度应为2-30")
            return null
        }

        // 验证开始日期大于结束日期
        val start = stringToLong(startDate.text.toString().trim())
        val end = stringToLong(endDate.text.toString().trim())
        if (end < start) {
            toast("开始日期大于结束日期")
            return null
        }

        // 验证项目介绍内容不超过2000字
        val jLength = primaryJob.text.length
        if (!(jLength in 1..2000)) {
            toast("项目介绍内容长度应为2-2000")
            return null
        }

        //验证非空 (项目链接可空)
        if (projectName.text.equals("")) {
            toast("公司名字为空")
            return null
        }
        if (position.text.equals("")) {
            toast("项目中的职位为空")
            return null
        }
        if (primaryJob.text.equals("")) {
            toast("项目介绍为空")
            return null
        }

        return mapOf(
            "attributes" to mapOf(
                "projectUrl" to projectUrl.text.toString().trim()
            ),
            "endDate" to stringToLong(endDate.text.toString().trim()).toString(),
            "projectName" to projectName.text.toString().trim(),
            "position" to position.text.toString().trim(),
            "responsibility" to primaryJob.text.toString().trim(),
            "startDate" to stringToLong(startDate.text.toString().trim()).toString()
        )
    }

    fun setStartDate(date: String) {
        startDate.text = date
    }

    fun setEndDate(date: String) {
        endDate.text = date
    }

    private fun createView(): View? {
        return UI {
            linearLayout {
                scrollView {
                    isVerticalScrollBarEnabled = false
                    verticalLayout {
                        // プロジェクト名
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "プロジェクト名"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            projectName = editText {
                                background = null
                                padding = dip(1)
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(85)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 担当役職
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "担当役職"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            position = editText {
                                background = null
                                padding = dip(1)
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(85)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 開始時間
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "開始時間"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                startDate = textView {
                                    text = "開始時間を選択する"
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                toolbar {
                                    navigationIconResource = R.mipmap.icon_go_position
                                    onClick {
                                        editproject.startDate()
                                    }
                                }.lparams {
                                    width = dip(22)
                                    height = dip(22)
                                    alignParentRight()
                                    centerVertically()
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // 終了時間
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "終了時間"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                endDate = textView {
                                    text = "終了時間を選択する"
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                toolbar {
                                    navigationIconResource = R.mipmap.icon_go_position
                                    onClick {
                                        editproject.endDate()
                                    }
                                }.lparams {
                                    width = dip(22)
                                    height = dip(22)
                                    alignParentRight()
                                    centerVertically()
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // プロジェクトのリンク
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "プロジェクトのリンク"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            projectUrl = editText {
                                background = null
                                padding = dip(1)
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(85)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                        // プロジェクト紹介
                        relativeLayout {
                            textView {
                                text = "プロジェクト紹介"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            primaryJob = editText {
                                backgroundResource = R.drawable.area_text
                                gravity = top
                                padding = dip(10)
                            }.lparams {
                                width = matchParent
                                height = dip(170)
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(220)
                            leftMargin = dip(15)
                            rightMargin = dip(15)
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                }.lparams {
                    width = matchParent
                    height = matchParent
                }
            }
        }.view
    }

    // 类型转换
    private fun longToString(long: Long): String {
        val str = SimpleDateFormat("yyyy-MM-dd").format(Date(long))
        return str
    }

    // 类型转换
    private fun stringToLong(str: String): Long {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(str)
        return date.time
    }
}