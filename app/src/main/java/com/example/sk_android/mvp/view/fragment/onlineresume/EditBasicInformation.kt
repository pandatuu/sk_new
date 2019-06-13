package com.example.sk_android.mvp.view.fragment.onlineresume

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.Sex
import com.example.sk_android.mvp.model.onlineresume.UserBasicInformation
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import java.text.SimpleDateFormat
import java.util.*


class EditBasicInformation : Fragment() {

    interface Middleware {

        fun addListFragment(title: String, list: MutableList<String>)
        fun birthdateclick(text: String)
        fun jobdateClick(text: String)

    }

    companion object {

        fun newInstance(): EditBasicInformation {
            val fragment = EditBasicInformation()

//            val bundle = Bundle()
//            bundle.putParcelable("user", user)
//            fragment.arguments = bundle

            return fragment
        }

    }

    private val sexList = mutableListOf<String>("男", "女")
    private val chooseList = mutableListOf<String>("撮影", "アルバムから選ぶ", "黙認")
    private lateinit var middleware: Middleware
    private lateinit var basic: UserBasicInformation

    private lateinit var image: ImageView
    private lateinit var uri: String
    private lateinit var name: EditText
    private lateinit var sex: TextView
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var line: EditText
    private lateinit var birth: TextView
    private lateinit var jobDate: TextView
    private lateinit var userSkill: EditText
    private lateinit var jobSkill: EditText
    private lateinit var iCanDo: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView = createView()
        middleware = activity as Middleware
        val dialog = DatePickDialog(context)
        //设置上下年分限制
        dialog.setYearLimt(5)
        //设置标题
        dialog.setTitle("选择时间")
        //设置类型
        dialog.setType(DateType.TYPE_ALL)
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd HH:mm")
//        val user = savedInstanceState?.getParcelable<UserBasicInformation>("user")

        return fragmentView
    }

    fun setUserBasicInfo(info: UserBasicInformation) {
        basic = info
        uri = info.avatarURL

        //加载网络图片
        interPic(uri)

        name.text = SpannableStringBuilder(info.firstName+" "+info.lastName)
        sex.text = if (info.gender == Sex.MALE) "男" else "女"
        phone.text = SpannableStringBuilder(info.phone)
        email.text = SpannableStringBuilder(info.email)
        line.text = SpannableStringBuilder(info.line)
        birth.text = longToString(info.birthday)
        jobDate.text = longToString(info.workingStartDate)
        userSkill.text = SpannableStringBuilder(info.attributes.userSkill)
        jobSkill.text = SpannableStringBuilder(info.attributes.jobSkill)
        iCanDo.text = SpannableStringBuilder(info.attributes.iCanDo)
    }

    fun setImage(url: String) {//网络地址
        uri = url.substring(1,url.length-1)
        interPic(uri)
    }

    fun setBirthday(date: String) {
        birth.text = date
    }

    fun setJobDate(date: String) {
        jobDate.text = date
    }

    fun setSex(str: String) {
        sex.text = str
    }

    //获取当前表单信息
    fun getBasic(): UserBasicInformation {
        val name = name.text.toString().trim()
        val nameArray = name.split(" ")

        basic.avatarURL = uri
        basic.firstName = nameArray[0]
        basic.lastName = nameArray[1]
        basic.gender = if (sex.text.toString().equals("男")) Sex.MALE else Sex.FEMALE
        basic.phone = phone.text.toString().trim()
        basic.email = email.text.toString().trim()
        basic.line = line.text.toString().trim()
        basic.birthday = stringToLong(birth.text.toString().trim())
        basic.workingStartDate = stringToLong(jobDate.text.toString().trim())
        basic.attributes.userSkill = userSkill.text.toString().trim()
        basic.attributes.jobSkill = jobSkill.text.toString().trim()
        basic.attributes.iCanDo = iCanDo.text.toString().trim()

        return basic
    }


    private fun createView(): View? {
        return UI {
            linearLayout {
                scrollView {
                    verticalLayout {
                        // Head frame
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "アバター"
                                textSize = 17f
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                centerVertically()
                            }
                            image = imageView {
                                imageResource = R.mipmap.sk
                                setOnClickListener { middleware.addListFragment("顔", chooseList) }
                            }.lparams {
                                width = dip(60)
                                height = dip(60)
                                alignParentRight()
                                centerVertically()
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(100)
                        }
                        // Name
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "お名前"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            name = editText {
                                background = null
                                padding = dip(1)
                                text = SpannableStringBuilder("")
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
                        }
                        // Sex
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "性别"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                sex = textView {
                                    text = ""
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = wrapContent
                                    height = wrapContent
                                    topMargin = dip(15)
                                    centerVertically()
                                }
                                var tool = toolbar {
                                    navigationIconResource = R.mipmap.icon_go_position
                                }.lparams {
                                    width = dip(22)
                                    height = dip(22)
                                    alignParentRight()
                                    centerVertically()
                                }
                                onClick {
                                    middleware.addListFragment("性别", sexList)
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Phone
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "電話番号"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            phone = editText {
                                background = null
                                padding = dip(1)
                                text = SpannableStringBuilder("")
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Email
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "メールアドレス"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            email = editText {
                                background = null
                                padding = dip(1)
                                text = SpannableStringBuilder("")
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Line
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "Line番号"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            line = editText {
                                background = null
                                padding = dip(1)
                                text = SpannableStringBuilder("")
                                textSize = 17f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams {
                                width = matchParent
                                height = wrapContent
                                topMargin = dip(45)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Birth Date
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "出生年月"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                birth = textView {
                                    text = ""
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
                                }.lparams {
                                    width = dip(22)
                                    height = dip(22)
                                    alignParentRight()
                                    centerVertically()
                                }
                                onClick {
                                    middleware.birthdateclick("birthday")
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Job Date
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "就職期日"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                jobDate = textView {
                                    text = ""
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
                                }.lparams {
                                    width = dip(22)
                                    height = dip(22)
                                    alignParentRight()
                                    centerVertically()
                                }
                                onClick {
                                    middleware.jobdateClick("jobDate")
                                }
                            }.lparams {
                                width = wrapContent
                                height = matchParent
                                topMargin = dip(25)
                            }
                        }.lparams {
                            width = matchParent
                            height = dip(80)
                        }
                        // Job Skill
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "仕事スキル"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                jobSkill = editText {
                                    background = null
                                    padding = dip(1)
                                    text = SpannableStringBuilder("")
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    topMargin = dip(15)
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
                        }
                        // My Skill
                        relativeLayout {
                            backgroundResource = R.drawable.text_view_bottom_border
                            textView {
                                text = "個人スキル"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            relativeLayout {
                                userSkill = editText {
                                    background = null
                                    padding = dip(1)
                                    text = SpannableStringBuilder("")
                                    textSize = 17f
                                    textColor = Color.parseColor("#FF333333")
                                }.lparams {
                                    width = matchParent
                                    height = wrapContent
                                    topMargin = dip(15)
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
                        }
                        // EditView
                        relativeLayout {
                            textView {
                                text = "私ができること"
                                textSize = 14f
                                textColor = Color.parseColor("#FF999999")
                            }.lparams {
                                width = wrapContent
                                height = wrapContent
                                topMargin = dip(15)
                            }
                            iCanDo = editText {
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
                        }
                    }.lparams {
                        width = matchParent
                        height = matchParent
                        leftMargin = dip(15)
                        rightMargin = dip(15)
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

    //获取网络图片
    private fun interPic(url : String){
        Glide.with(this)
            .asBitmap()
            .load(url)
            .into(image)
    }
}