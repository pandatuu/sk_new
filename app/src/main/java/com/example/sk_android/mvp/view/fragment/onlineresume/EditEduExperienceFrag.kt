package com.example.sk_android.mvp.view.fragment.onlineresume

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
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduBack
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import java.text.SimpleDateFormat
import java.util.*


class EditEduExperienceFrag : Fragment() {

    interface EditEdu {
        fun startDate()
        fun endDate()
        fun eduBackground(text: String)
    }

    private lateinit var editEdu: EditEdu

    private lateinit var schoolName: EditText //学校名字
    private lateinit var eduBackground: TextView //教育背景
    private lateinit var major: EditText //专业
    private lateinit var startDate: TextView //开始日期
    private lateinit var endDate: TextView //结束日期
    private lateinit var awards: EditText //获得奖项

    companion object {
        fun newInstance(): EditEduExperienceFrag {
            val fragment = EditEduExperienceFrag()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        editEdu = activity as EditEdu
        return createView()
    }

    fun setEduExperience(obj: EduExperienceModel){
        var back =  enumToString(obj.educationalBackground)?:""

        schoolName.text = SpannableStringBuilder(obj.schoolName)
        eduBackground.text = back
        major.text = SpannableStringBuilder(obj.major)
        startDate.text = longToString(obj.startDate)
        endDate.text = longToString(obj.endDate)
        awards.text = SpannableStringBuilder(obj.attributes.awards)
    }

    fun getEduExperience(): Map<String, Any>? {
        var back =  stringToEnum(eduBackground.text.toString().trim())?:""

        val bool = true
        if (bool) {
            return mapOf(
                "attributes" to mapOf(
                    "awards" to awards.text.toString().trim()
                ),
                "endDate" to stringToLong(endDate.text.toString().trim()).toString(),
                "educationalBackground" to back,
                "major" to major.text.toString().trim(),
//                "schoolId" to primaryJob.text.toString().trim(),
                "schoolName" to schoolName.text.toString().trim(),
                "startDate" to stringToLong(startDate.text.toString().trim()).toString()
            )
        } else {
            return null
        }
    }

    fun setStartDate(date: String) {
        startDate.text = date
    }

    fun setEndDate(date: String) {
        endDate.text = date
    }

    fun seteduBack(back: String) {
        eduBackground.text = back
    }

    private fun createView(): View? {
        return UI {
            linearLayout {
                scrollView {
                    verticalLayout {
                        // 学校名
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "学校名"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            schoolName = editText {
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
                        // 学歴
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "学歴"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                eduBackground = textView {
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
                                        editEdu.eduBackground(schoolName.text.toString().trim())
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
                        // 専門科目
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "専門科目"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            major = editText {
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
                                        editEdu.startDate()
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
                                        editEdu.endDate()
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
                        // 獲得した賞
                        relativeLayout {
                            textView {
                                text = "獲得した賞"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            awards = editText {
                                backgroundResource = R.drawable.area_text
                                gravity = top
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

    //string跟Enum匹配
    private fun stringToEnum(edu: String): String?{
        when(edu){
            "中学及以下" -> return EduBack.MIDDLE_SCHOOL.toString()
            "高中" -> return EduBack.HIGH_SCHOOL.toString()
            "专门学校" -> return EduBack.SHORT_TERM_COLLEGE.toString()
            "学士" -> return EduBack.BACHELOR.toString()
            "硕士" -> return EduBack.MASTER.toString()
            "博士" -> return EduBack.DOCTOR.toString()
        }
        return null
    }
    private fun enumToString(edu: EduBack): String?{
        when(edu){
            EduBack.MIDDLE_SCHOOL -> return "中学及以下"
            EduBack.HIGH_SCHOOL -> return "高中"
            EduBack.SHORT_TERM_COLLEGE -> return "专门学校"
            EduBack.BACHELOR -> return "学士"
            EduBack.MASTER -> return "硕士"
            EduBack.DOCTOR -> return "博士"
        }
        return null
    }
}