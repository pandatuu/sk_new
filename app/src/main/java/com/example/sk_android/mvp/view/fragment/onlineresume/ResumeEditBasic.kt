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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import click
import com.bumptech.glide.Glide
import com.example.sk_android.R
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.onlineresume.basicinformation.Sex
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

    var actionBarNormalFragment: ResumeEditBarFrag? = null
    private lateinit var user: UserResume
    //用户基本信息
    private lateinit var image: ImageView
    private lateinit var displayName: TextView
    private lateinit var age: TextView
    private lateinit var eduBack: TextView
    private lateinit var workDate: TextView
    private lateinit var iCanDo: TextView
    private lateinit var firstview: View
    private lateinit var lastview: View

    companion object {

        var myResult: ArrayList<UserBasicInformation> = arrayListOf()
        fun newInstance(actionBarNormalFragment: ResumeEditBarFrag?): ResumeEditBasic {
            val frag = ResumeEditBasic()
            frag.actionBarNormalFragment = actionBarNormalFragment
            return frag
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        user = activity as UserResume
        return creatV()
    }


    @SuppressLint("SetTextI18n")
    fun setUserBasicInfo(info: UserBasicInformation) {
        if (info.gender == Sex.MALE) {
            //加载网络图片
            interPic(info.avatarURL, R.mipmap.person_man)
        } else {
            //加载网络图片
            interPic(info.avatarURL, R.mipmap.person_woman)
        }
        val year = Calendar.getInstance().get(Calendar.YEAR)
        //姓名
        displayName.text = info.displayName
        //岁数
        if (info.birthday != 0L) {
            val userAge = year - longToString(info.birthday).substring(0, 4).toInt()
            age.text = "${userAge}歳"
            firstview.visibility = LinearLayout.VISIBLE
            age.visibility = LinearLayout.VISIBLE
        }
        //教育背景
        eduBack.text = if(info.educationalBackground != null) enumToString(info.educationalBackground) else ""
        lastview.visibility = LinearLayout.VISIBLE
        eduBack.visibility = LinearLayout.VISIBLE
        //工作年限
        if (info.workingStartDate != 0L) {
            val work = year - longToString(info.workingStartDate).substring(0, 4).toInt()
            when {
                work < 5 -> workDate.text = "5年以内"
                work in 5..9 -> workDate.text = "5-10年"
                else -> workDate.text = "10年以上"
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
        val view = UI {
            verticalLayout {
                relativeLayout {
                    backgroundResource = R.drawable.text_view_bottom_border
                    linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        gravity = Gravity.CENTER_VERTICAL
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            displayName = textView {
                                textSize = 24f
                                textColor = Color.BLACK
                                typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                                maxLines = 1
                                ellipsize = TextUtils.TruncateAt.END
                                maxWidth = dip(200)
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
                        }.lparams(dip(230), wrapContent)
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
                        imageResource = R.mipmap.default_avatar
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
        initView(1)
        return view
    }

    fun initView(from: Int) {
        if (from == 1) {
            val application: App? = App.getInstance()
            application?.setResumeEditBasic(this)
        }
        if (myResult.size == 0) {
            //第一次进入
        } else {
            println("=====basic=====")
            println(myResult)
            println(from)
            println("=====basic=====")
            if (myResult[0].changedContent != null) {
                setUserBasicInfo(myResult[0].changedContent!!)
                if (myResult[0].changedContent?.displayName!!.isNotBlank()) {
                    actionBarNormalFragment?.setTiltle(myResult[0].changedContent!!.displayName)
                } else {
                    actionBarNormalFragment?.setTiltle("")
                }
            } else {
                setUserBasicInfo(myResult[0])
                if (myResult[0].displayName.isNotBlank()) {
                    actionBarNormalFragment?.setTiltle(myResult[0].displayName)
                } else {
                    actionBarNormalFragment?.setTiltle("")
                }
            }
        }
    }

    // 类型转换
    @SuppressLint("SimpleDateFormat")
    private fun longToString(long: Long): String {
        return SimpleDateFormat("yyyy-MM-dd").format(Date(long))
    }

//    // 类型转换
//    @SuppressLint("SimpleDateFormat")
//    private fun stringToLong(str: String): Long {
//        val date = SimpleDateFormat("yyyy-MM-dd").parse(str)
//        return date.time
//    }

    //获取网络图片
    private fun interPic(url: String, default: Int) {
        Glide.with(this)
            .asBitmap()
            .load(url)
            .placeholder(default)
            .into(image)
    }

    private fun enumToString(edu: EduBack?): String {
        return when (edu) {
            EduBack.MIDDLE_SCHOOL -> "中卒"
            EduBack.HIGH_SCHOOL -> "高卒"
            EduBack.SHORT_TERM_COLLEGE -> "専門卒・短大卒"
            EduBack.BACHELOR -> "大卒"
            EduBack.MASTER -> "修士"
            EduBack.DOCTOR -> "博士"
            else -> ""
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val application: App? = App.getInstance()
        application!!.setResumeEditBasic(null)
    }
}