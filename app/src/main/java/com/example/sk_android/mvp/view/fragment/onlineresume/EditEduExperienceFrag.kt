package com.example.sk_android.mvp.view.fragment.onlineresume

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import click
import com.example.sk_android.R
import com.example.sk_android.custom.layout.floatOnKeyboardLayout
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduBack
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger
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
            return EditEduExperienceFrag()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        editEdu = activity as EditEdu
        return createView()
    }

    fun setEduExperience(obj: EduExperienceModel) {
        val back = enumToString(obj.educationalBackground) ?: ""

        schoolName.text = SpannableStringBuilder(obj.schoolName?:"")
        eduBackground.text = back
        major.text = SpannableStringBuilder(obj.major?:"")
        startDate.text = longToString(obj.startDate)
        endDate.text = longToString(obj.endDate)
        awards.text = SpannableStringBuilder(obj.attributes.awards?:"")
    }

    fun getEduExperience(): Map<String, Any>? {
        var back = stringToEnum(eduBackground.text.toString().trim()) ?: ""
        when (eduBackground.text) {
            "中卒" -> back = EduBack.MIDDLE_SCHOOL.toString()
            "高卒" -> back = EduBack.HIGH_SCHOOL.toString()
            "専門卒・短大卒" -> back = EduBack.SHORT_TERM_COLLEGE.toString()
            "大卒" -> back = EduBack.BACHELOR.toString()
            "修士" -> back = EduBack.MASTER.toString()
            "博士" -> back = EduBack.DOCTOR.toString()
        }
        //验证学校非空 (获得奖项可空)
        if (schoolName.text.isNullOrBlank()) {
            val toast = Toast.makeText(activity!!.applicationContext, "学校名を入力してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        //验证学校名字字符长度 2-20
        val sLength = schoolName.text.length
        if (sLength !in 2..20) {
            val toast = Toast.makeText(activity!!.applicationContext, "学校名を2～20文字以内でご記入ください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        //验证学历非空
        if (eduBackground.text.isNullOrBlank()) {
            val toast = Toast.makeText(activity!!.applicationContext, "学歴を選択してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        //验证专业非空
        if (major.text.isNullOrBlank()) {
            val toast = Toast.makeText(activity!!.applicationContext, "専攻を入力してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }


        //验证专业字符长度 2-20
        val mLength = major.text.length
        if (mLength !in 2..20) {
            val toast = Toast.makeText(activity!!.applicationContext, "専攻を2～20文字以内でご記入ください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }

        // 验证开始日期大于结束日期和非空
        if (startDate.text.toString().isNotBlank() && endDate.text.toString().isNotBlank()) {
            val start = stringToLong(startDate.text.toString().trim())
            val end = stringToLong(endDate.text.toString().trim())
            if (end < start) {
                val toast = Toast.makeText(activity!!.applicationContext, "終了時間は開始時間より後に設定してください。", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                return null
            }
        } else {
            val toast = Toast.makeText(activity!!.applicationContext, "開始時間あるいは終了時間を選択してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }

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
            floatOnKeyboardLayout {
                linearLayout {
                    scrollView {
                        isVerticalScrollBarEnabled = false
                        overScrollMode = View.OVER_SCROLL_NEVER
                        verticalLayout {
                            // 学校名
                            relativeLayout {
                                backgroundResource = R.drawable.text_view_bottom_border
                                textView {
                                    text = "学校名"
                                    textSize = 14f
                                    textColor = Color.parseColor("#FF999999")
                                    singleLine = true
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
                                    singleLine = true
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
                                    imageView {
                                        imageResource = R.mipmap.icon_go_position
                                        this.withTrigger().click {
                                            closeKeyfocus()
                                            editEdu.eduBackground(schoolName.text.toString().trim())
                                        }
                                    }.lparams {
                                        width = dip(6)
                                        height = dip(11)
                                        alignParentRight()
                                        centerVertically()
                                    }
                                    this.withTrigger().click {
                                        closeKeyfocus()
                                        editEdu.eduBackground(schoolName.text.toString().trim())
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
                                    text = "専門"
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
                                    singleLine = true
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
                                        textSize = 17f
                                        textColor = Color.parseColor("#FF333333")
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        topMargin = dip(15)
                                        centerVertically()
                                    }
                                    imageView {
                                        imageResource = R.mipmap.icon_go_position
                                        this.withTrigger().click {
                                            closeKeyfocus()
                                            editEdu.startDate()
                                        }
                                    }.lparams {
                                        width = dip(6)
                                        height = dip(11)
                                        alignParentRight()
                                        centerVertically()
                                    }
                                    this.withTrigger().click {
                                        closeKeyfocus()
                                        editEdu.startDate()
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
                                        textSize = 17f
                                        textColor = Color.parseColor("#FF333333")
                                    }.lparams {
                                        width = wrapContent
                                        height = wrapContent
                                        topMargin = dip(15)
                                        centerVertically()
                                    }
                                    imageView {
                                        imageResource = R.mipmap.icon_go_position
                                        this.withTrigger().click {
                                            closeKeyfocus()
                                            editEdu.endDate()
                                        }
                                    }.lparams {
                                        width = dip(6)
                                        height = dip(11)
                                        alignParentRight()
                                        centerVertically()
                                    }
                                    this.withTrigger().click {
                                        closeKeyfocus()
                                        editEdu.endDate()
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
                                    padding = dip(10)
                                    setOnTouchListener(object : View.OnTouchListener {
                                        @SuppressLint("ClickableViewAccessibility")
                                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                                            if (event!!.action == MotionEvent.ACTION_DOWN
                                                || event.action == MotionEvent.ACTION_MOVE
                                            ) {
                                                //按下或滑动时请求父节点不拦截子节点
                                                v!!.parent.parent.parent.requestDisallowInterceptTouchEvent(true)
                                            }
                                            if (event.action == MotionEvent.ACTION_UP) {
                                                //抬起时请求父节点拦截子节点
                                                v!!.parent.parent.parent.requestDisallowInterceptTouchEvent(false)
                                            }
                                            return false
                                        }
                                    })
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
                            this.withTrigger().click {
                                closeKeyfocus()
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
                setAboutPopupListener {
                    val focusedView = findFocus()
                    if (focusedView != null) {
                        setAnchor(focusedView)
                    }
                }
            }
        }.view
    }

    // 类型转换
    @SuppressLint("SimpleDateFormat")
    private fun longToString(long: Long): String {
        return SimpleDateFormat("yyyy-MM-dd").format(Date(long))
    }

    // 类型转换
    @SuppressLint("SimpleDateFormat")
    private fun stringToLong(str: String): Long {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(str)
        return date.time
    }

    //string跟Enum匹配
    private fun stringToEnum(edu: String): String? {
        when (edu) {
            "中卒" -> return EduBack.MIDDLE_SCHOOL.toString()
            "高卒" -> return EduBack.HIGH_SCHOOL.toString()
            "専門卒・短大卒" -> return EduBack.SHORT_TERM_COLLEGE.toString()
            "大卒" -> return EduBack.BACHELOR.toString()
            "修士" -> return EduBack.MASTER.toString()
            "博士" -> return EduBack.DOCTOR.toString()
        }
        return null
    }

    private fun enumToString(edu: EduBack): String? {
        when (edu) {
            EduBack.MIDDLE_SCHOOL -> return "中卒"
            EduBack.HIGH_SCHOOL -> return "高卒"
            EduBack.SHORT_TERM_COLLEGE -> return "専門卒・短大卒"
            EduBack.BACHELOR -> return "大卒"
            EduBack.MASTER -> return "修士"
            EduBack.DOCTOR -> return "博士"
        }
        return null
    }

    private fun closeKeyfocus() {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        schoolName.clearFocus()
        major.clearFocus()
        awards.clearFocus()
    }
}