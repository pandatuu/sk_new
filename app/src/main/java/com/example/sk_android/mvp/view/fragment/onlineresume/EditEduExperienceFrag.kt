package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduBack
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduExperienceModel
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.toast
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

        //验证非空 (获得奖项可空)
        if(schoolName.text.equals("")){
            toast("学校名字为空")
            return null
        }
        if(eduBackground.text.equals("")){
            toast("教育背景为空")
            return null
        }
        if(major.text.equals("")){
            toast("专业为空")
            return null
        }

        //验证学校名字字符长度 4-20
        val sLength = schoolName.text.length
        if (sLength !in 4..20) {
            toast("学校名字长度应为4-20")
            return null
        }

        //验证专业字符长度 2-20
        val mLength = major.text.length
        if (mLength !in 2..20) {
            toast("专业应为2-20")
            return null
        }

        // 验证开始日期大于结束日期
        if(startDate.text.toString()!="" && endDate.text.toString()!=""){
            val start = stringToLong(startDate.text.toString().trim())
            val end = stringToLong(endDate.text.toString().trim())
            if (end < start) {
                toast("开始日期大于结束日期")
                return null
            }
        }else{
            toast("开始日期或结束日期未填写")
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
                                    onClick {
                                        closeKeyfocus()
                                        editEdu.eduBackground(schoolName.text.toString().trim())
                                    }
                                }.lparams {
                                    width = dip(6)
                                    height = dip(11)
                                    alignParentRight()
                                    centerVertically()
                                }
                                onClick {
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
                                    onClick {
                                        closeKeyfocus()
                                        editEdu.startDate()
                                    }
                                }.lparams {
                                    width = dip(6)
                                    height = dip(11)
                                    alignParentRight()
                                    centerVertically()
                                }
                                onClick {
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
                                    onClick {
                                        closeKeyfocus()
                                        editEdu.endDate()
                                    }
                                }.lparams {
                                    width = dip(6)
                                    height = dip(11)
                                    alignParentRight()
                                    centerVertically()
                                }
                                onClick {
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
                                setOnTouchListener(object: View.OnTouchListener{
                                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                                        if(event!!.action == MotionEvent.ACTION_DOWN
                                            || event!!.action == MotionEvent.ACTION_MOVE){
                                            //按下或滑动时请求父节点不拦截子节点
                                            v!!.parent.parent.parent.requestDisallowInterceptTouchEvent(true);
                                        }
                                        if(event!!.action == MotionEvent.ACTION_UP){
                                            //抬起时请求父节点拦截子节点
                                            v!!.parent.parent.parent.requestDisallowInterceptTouchEvent(false);
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
                        onClick {
                            closeKeyfocus()
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                    }
                    setOnScrollChangeListener(object: View.OnScrollChangeListener{
                        override fun onScrollChange(
                            v: View?,
                            scrollX: Int,
                            scrollY: Int,
                            oldScrollX: Int,
                            oldScrollY: Int
                        ) {
                            val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
                        }

                    })
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

    private fun closeKeyfocus(){
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        schoolName.clearFocus()
        major.clearFocus()
        awards.clearFocus()
    }
}