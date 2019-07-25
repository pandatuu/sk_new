package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.mvp.model.onlineresume.eduexperience.EduBack
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import withTrigger
import java.text.SimpleDateFormat
import java.util.*

class ResumeEditBasic : Fragment() {
    interface UserResume {
        fun jumpToBasic()
    }

    private lateinit var user: UserResume
    //用户基本信息
    private lateinit var image: ImageView
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var age: TextView
    private lateinit var eduBack: TextView
    private lateinit var workDate: TextView
    private lateinit var iCanDo: TextView
    private lateinit var firstview: View
    private lateinit var lastview: View

    companion object {
        fun newInstance(): ResumeEditBasic {
            return ResumeEditBasic()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        user = activity as UserResume
        return creatV()
    }


    fun setUserBasicInfo(info: UserBasicInformation) {
        //加载网络图片
        interPic(info.avatarURL)
        val year = Calendar.getInstance().get(Calendar.YEAR)
        //姓名
        firstName.text = info.firstName
        lastName.text = info.lastName
        //岁数
        if (info.birthday != 0L) {
            val userAge = year - longToString(info.birthday).substring(0, 4).toInt()
            age.text = "${userAge}歳"
            firstview.visibility = LinearLayout.VISIBLE
            age.visibility = LinearLayout.VISIBLE
        }
        //教育背景
        if (info.educationalBackground != null) {
            eduBack.text = enumToString(info.educationalBackground)
            lastview.visibility = LinearLayout.VISIBLE
            eduBack.visibility = LinearLayout.VISIBLE
        }
        //工作年限
        if (info.workingStartDate != 0L) {
            val work = year - longToString(info.workingStartDate).substring(0, 4).toInt()
            if (work < 5) {
                workDate.text = "5年以内"
            } else if (work >= 5 && work < 10) {
                workDate.text = "5-10年"
            } else {
                workDate.text = "10年以上"
            }
            workDate.visibility = LinearLayout.VISIBLE
        }
        //我能做的事
        if (info.attributes.iCanDo != "") {
            iCanDo.text = info.attributes.iCanDo
        } else {
            iCanDo.visibility = LinearLayout.GONE
        }
    }

    fun setBackground(back: EduBack) {
        //教育背景
        eduBack.text = enumToString(back)
        eduBack.visibility = LinearLayout.VISIBLE
        lastview.visibility = LinearLayout.VISIBLE
    }

    fun creatV(): View {
        return UI {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER_VERTICAL
                        lastName = textView {
                            textSize = 24f
                            textColor = Color.BLACK
                            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                        }
                        firstName = textView {
                            textSize = 24f
                            textColor = Color.BLACK
                            typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(5)
                        }
                        imageView {
                            imageResource = R.mipmap.edit_icon
                            this.withTrigger().click {
                                user.jumpToBasic()
                            }
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(10)
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        topMargin = dip(18)
                    }
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        age = textView {
                            textSize = 13f
                            textColor = Color.parseColor("#FF666666")
                            visibility = LinearLayout.GONE
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                        }
                        firstview = view {
                            backgroundColor = Color.parseColor("#FF000000")
                            visibility = LinearLayout.GONE
                        }.lparams {
                            width = dip(1)
                            height = dip(12)
                            leftMargin = dip(5)
                            gravity = Gravity.CENTER_VERTICAL
                        }
                        eduBack = textView {
                            textSize = 13f
                            textColor = Color.parseColor("#FF666666")
                            visibility = LinearLayout.GONE
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(5)
                        }
                        lastview = view {
                            backgroundColor = Color.parseColor("#FF000000")
                            visibility = LinearLayout.GONE
                        }.lparams {
                            width = dip(1)
                            height = dip(12)
                            leftMargin = dip(5)
                            gravity = Gravity.CENTER_VERTICAL
                        }
                        workDate = textView {
                            textSize = 13f
                            textColor = Color.parseColor("#FF666666")
                            visibility = LinearLayout.GONE
                        }.lparams {
                            width = wrapContent
                            height = wrapContent
                            leftMargin = dip(5)
                        }
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        topMargin = dip(65)
                    }
                    image = imageView {
                        imageResource = R.mipmap.sk
                    }.lparams {
                        width = dip(70)
                        height = dip(70)
                        centerVertically()
                        alignParentRight()
                    }
                    this.withTrigger().click {
                        user.jumpToBasic()
                    }
                }.lparams {
                    width = matchParent
                    height = dip(100)
                    leftMargin = dip(15)
                    rightMargin = dip(15)
                }
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    iCanDo = textView {
                        textSize = 12f
                        textColor = Color.parseColor("#FF333333")
                    }.lparams {
                        width = wrapContent
                        height = wrapContent
                        topMargin = dip(15)
                        bottomMargin = dip(15)
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
        val str = SimpleDateFormat("yyyy-MM-dd").format(Date(long))
        return str
    }

    // 类型转换
    private fun stringToLong(str: String): Long {
        val date = SimpleDateFormat("yyyy-MM-dd").parse(str)
        return date.time
    }

    //获取网络图片
    private fun interPic(url: String) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .placeholder(R.mipmap.default_avatar)
            .into(image)
    }

    private fun enumToString(edu: EduBack): String {
        when (edu) {
            EduBack.MIDDLE_SCHOOL -> return "中学卒業及び以下"
            EduBack.HIGH_SCHOOL -> return "高卒"
            EduBack.SHORT_TERM_COLLEGE -> return "専門卒・短大卒"
            EduBack.BACHELOR -> return "大卒"
            EduBack.MASTER -> return "修士"
            EduBack.DOCTOR -> return "博士"
        }
    }
}