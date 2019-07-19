package com.example.sk_android.mvp.view.fragment.onlineresume

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputFilter
import android.text.InputType
import android.text.SpannableStringBuilder
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import click
import com.bumptech.glide.Glide
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.example.sk_android.R
import com.example.sk_android.mvp.model.onlineresume.basicinformation.Sex
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.utils.roundImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.UI
import org.jetbrains.anko.support.v4.toast
import withTrigger
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class EditBasicInformation : Fragment() {

    interface Middleware {

        fun addListFragment(title: String, list: MutableList<String>)
        fun birthdateclick(text: String)
        fun jobdateClick(text: String)

    }

    companion object {

        fun newInstance(): EditBasicInformation {
            val fragment = EditBasicInformation()

            return fragment
        }

    }

    private val chooseList = mutableListOf<String>("自定する", "黙認")
    private lateinit var middleware: Middleware
    private lateinit var basic: UserBasicInformation
    private lateinit var uri: String
    private lateinit var sexValue: Sex

    private lateinit var image: ImageView
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var sex: RadioGroup
    private lateinit var phone: EditText
    private lateinit var email: EditText
    private lateinit var line: EditText
    private lateinit var birthDate: TextView
    private lateinit var jobDate: TextView
    private lateinit var userSkill: EditText
    private lateinit var jobSkill: EditText
    private lateinit var iCanDo: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        middleware = activity as Middleware
        var fragmentView = createView()
        val dialog = DatePickDialog(context)
        //设置上下年分限制
        dialog.setYearLimt(5)
        //设置标题
        dialog.setTitle("选择时间")
        //设置类型
        dialog.setType(DateType.TYPE_ALL)
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd")
//        val user = savedInstanceState?.getParcelable<UserBasicInformation>("user")

        return fragmentView
    }

    fun setUserBasicInfo(info: UserBasicInformation) {
        basic = info
        uri = info.avatarURL

        //加载网络图片
        interPic(uri)

        firstName.text = SpannableStringBuilder(info.firstName)
        lastName.text = SpannableStringBuilder(info.lastName)
        sex.check(if (info.gender == Sex.MALE) 1 else 2)
        phone.text = SpannableStringBuilder(info.phone)
        email.text = SpannableStringBuilder(info.email)
        line.text = SpannableStringBuilder(info.line)
        birthDate.text = longToString(info.birthday)
        jobDate.text = longToString(info.workingStartDate)
        userSkill.text = SpannableStringBuilder(info.attributes.userSkill)
        jobSkill.text = SpannableStringBuilder(info.attributes.jobSkill)
        iCanDo.text = SpannableStringBuilder(info.attributes.iCanDo)
    }

    fun setImage(url: String) {//网络地址
        uri = url
        interPic(url)
    }

    fun setDefaultImg(){
        image.setImageResource(R.mipmap.default_avatar)
    }

    fun setBirthday(date: String) {
        birthDate.text = date
    }

    fun setJobDate(date: String) {
        jobDate.text = date
    }

    fun setSex(str: String) {
        sex.check(if (str == Sex.MALE.toString()) 1 else 2)
    }

    //获取当前表单信息
    fun getBasic(): UserBasicInformation? {
        val firstName = firstName.text.toString().trim()
        val lastName = lastName.text.toString().trim()
        val gender = sexValue
        val phoneNum = phone.text.toString().trim()
        val emailNum = email.text.toString().trim()
        val line = line.text.toString().trim()
        val birth = stringToLong(birthDate.text.toString().trim())
        val job = stringToLong(jobDate.text.toString().trim())
        val personSkill = userSkill.text.toString().trim()
        val workSkill = jobSkill.text.toString().trim()
        val todo = iCanDo.text.toString().trim()

        //验证非空 (line可空)
        if (firstName == "" || lastName == "") {
            val toast = Toast.makeText(activity!!.applicationContext, "姓名输入为空", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }
        if (phoneNum == "") {
            val toast = Toast.makeText(activity!!.applicationContext, "手机号输入为空", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }
        if (emailNum == "") {
            val toast = Toast.makeText(activity!!.applicationContext, "email输入为空", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }
        if (personSkill == "") {
            val toast = Toast.makeText(activity!!.applicationContext, "个人技能输入为空", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }
        if (workSkill == "") {
            val toast = Toast.makeText(activity!!.applicationContext, "工作技能输入为空", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }
        if (todo == "") {
            val toast = Toast.makeText(activity!!.applicationContext, "我能做的输入为空", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }

        // 验证出生日期大于工作日期
        if (job <= birth) {
            val toast = Toast.makeText(activity!!.applicationContext, "工作日期大于出生日期", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }

        // 验证我能做的不超过2000字
        if (todo.length !in 2..2000 && todo != "") {
            val toast = Toast.makeText(activity!!.applicationContext, "我能做的超过2000字", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()
            return null
        }



        basic.avatarURL = uri
        basic.firstName = firstName
        basic.lastName = lastName
        basic.displayName = "$lastName $firstName"
        basic.gender = gender
        basic.phone = phoneNum
        basic.email = emailNum
        basic.line = line
        basic.birthday = birth
        basic.workingStartDate = job
        basic.attributes.userSkill = personSkill
        basic.attributes.jobSkill = workSkill
        basic.attributes.iCanDo = todo
        return basic
    }


    private fun createView(): View? {
        return UI {
            linearLayout {
                scrollView {
                    isVerticalScrollBarEnabled = false
                    overScrollMode = View.OVER_SCROLL_NEVER
                    linearLayout {
                        orientation = LinearLayout.VERTICAL
                        relativeLayout {
                            image = roundImageView {
                                imageResource = R.mipmap.default_avatar
                                this.withTrigger().click { middleware.addListFragment("颜", chooseList) }
                            }.lparams(dip(70), dip(70)) {
                                centerInParent()
                            }
                        }.lparams(matchParent, dip(100))
                        //name
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            backgroundResource = R.drawable.input_border
                            textView {
                                text = "名前"
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(wrapContent, matchParent)

                            relativeLayout {
                                lastName = editText {
                                    background = null
                                    hint = "苗字"
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    textSize = 15f
                                    singleLine = true
                                }.lparams(dip(50), matchParent) {
                                    alignParentRight()
                                    rightMargin = dip(60)
                                }
                                firstName = editText {
                                    background = null
                                    hint = "名前"
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    textSize = 15f
                                    singleLine = true
                                }.lparams(dip(50), matchParent) {
                                    alignParentRight()
                                }
                            }.lparams(matchParent, matchParent) {
                                rightMargin = dip(30)
                            }
                        }.lparams(matchParent, dip(44))
                        //性别
                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            backgroundResource = R.drawable.input_border
                            textView {
                                text = "性别"
                                textSize = 15f
                                textColor = Color.parseColor("#FF333333")
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(dip(100), matchParent)
                            relativeLayout {
                                sex = radioGroup {
                                    orientation = LinearLayout.HORIZONTAL
                                    val man = 1
                                    val feman = 2
                                    radioButton {
                                        id = man
                                        if (isChecked) {
                                            buttonDrawableResource = R.mipmap.register_ico_man_pre
                                            textColor = Color.parseColor("#FF202020")
                                        } else {
                                            buttonDrawableResource = R.mipmap.register_ico_man_nor
                                            textColor = Color.parseColor("#FFB3B3B3")
                                        }
                                        onCheckedChange { _, isChecked ->
                                            if (isChecked) {
                                                buttonDrawableResource = R.mipmap.register_ico_man_pre
                                                textColor = Color.parseColor("#FF202020")
                                                sexValue = Sex.MALE
                                            } else {
                                                buttonDrawableResource = R.mipmap.register_ico_man_nor
                                                textColor = Color.parseColor("#FFB3B3B3")
                                            }
                                        }
                                        text = "男性"
                                        textSize = 15f
                                    }.lparams(wrapContent, matchParent) {
                                        leftMargin = dip(15)
                                    }.lparams()
                                    radioButton {
                                        id = feman
                                        if (isChecked) {
                                            buttonDrawableResource = R.mipmap.register_ico_woman_pre
                                            textColor = Color.parseColor("#FF202020")
                                        } else {
                                            buttonDrawableResource = R.mipmap.register_ico_woman_nor
                                            textColor = Color.parseColor("#FFB3B3B3")
                                        }
                                        onCheckedChange { _, isChecked ->
                                            if (isChecked) {
                                                buttonDrawableResource = R.mipmap.register_ico_woman_pre
                                                textColor = Color.parseColor("#FF202020")
                                                sexValue = Sex.FEMALE
                                            } else {
                                                buttonDrawableResource = R.mipmap.register_ico_woman_nor
                                                textColor = Color.parseColor("#FFB3B3B3")
                                            }
                                        }
                                        text = "女性"
                                        textSize = 15f
                                    }.lparams(wrapContent, matchParent) {
                                        leftMargin = dip(15)
                                    }
                                }.lparams {
                                    centerVertically()
                                    alignParentRight()
                                }
                            }.lparams(matchParent, matchParent) {
                                rightMargin = dip(30)
                            }
                        }.lparams(matchParent, dip(44)) {
                            topMargin = dip(20)
                        }
                        //phone
                        relativeLayout {
                            backgroundResource = R.drawable.input_border
                            textView {
                                text = "携帯番号"
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(dip(110), matchParent) {
                                alignParentLeft()
                            }
                            phone = editText {
                                background = null
                                singleLine = true
                                inputType = InputType.TYPE_CLASS_PHONE
                                filters = arrayOf(InputFilter.LengthFilter(11))
                                textSize = 15f
                                isEnabled = false
                            }.lparams(dip(100), wrapContent) {
                                rightMargin = dip(30)
                                alignParentRight()
                            }
                        }.lparams(matchParent, dip(44)) {
                            topMargin = dip(20)
                        }
                        //email
                        relativeLayout {
                            backgroundResource = R.drawable.input_border
                            textView {
                                text = "メールアドレス"
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL

                            }.lparams(dip(110), matchParent)
                            email = editText {
                                background = null
                                singleLine = true
                                inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                                textSize = 15f
                                isEnabled = false
                            }.lparams(dip(150), wrapContent) {
                                alignParentRight()
                                rightMargin = dip(30)
                            }
                        }.lparams(matchParent, dip(44)) {
                            topMargin = dip(20)
                        }
                        //line
                        relativeLayout {
                            backgroundResource = R.drawable.input_border
                            textView {
                                text = "Line番号"
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(dip(110), matchParent)
                            line = editText {
                                backgroundColorResource = R.color.whiteFF
                                singleLine = true
                                hint = "cgland"
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                singleLine = true
                            }.lparams(wrapContent, wrapContent) {
                                alignParentRight()
                                rightMargin = dip(30)
                            }
                        }.lparams(matchParent, dip(44)) {
                            topMargin = dip(20)
                        }
                        //birth
                        relativeLayout {
                            backgroundResource = R.drawable.input_border
                            textView {
                                text = "生年月日"
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(wrapContent, matchParent) {
                            }
                            birthDate = textView {
                                text = ""
                                textSize = 15f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams(wrapContent, wrapContent) {
                                alignParentRight()
                                rightMargin = dip(30)
                                centerVertically()
                            }
                            imageView {
                                imageResource = R.mipmap.register_select_nor
                                this.withTrigger().click {
                                    closeKeyfocus()
                                    middleware.birthdateclick("birth")
                                }
                            }.lparams(dip(6), dip(11)) {
                                alignParentRight()
                                centerVertically()
                            }
                            this.withTrigger().onClick {
                                closeKeyfocus()
                                middleware.birthdateclick("birth")
                            }
                        }.lparams(matchParent, dip(44)) {
                            topMargin = dip(20)
                        }
                        //jobdate
                        relativeLayout {
                            backgroundResource = R.drawable.input_border
                            textView {
                                text = "初就職年月"
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(wrapContent, matchParent)
                            jobDate = textView {
                                text = ""
                                textSize = 15f
                                textColor = Color.parseColor("#FF333333")
                            }.lparams(wrapContent, wrapContent) {
                                alignParentRight()
                                rightMargin = dip(30)
                                centerVertically()
                            }
                            imageView {
                                imageResource = R.mipmap.register_select_nor
                                this.withTrigger().click {
                                    closeKeyfocus()
                                    middleware.jobdateClick("jobDate")
                                }
                            }.lparams(dip(6), dip(11)) {
                                alignParentRight()
                                centerVertically()
                            }
                            this.withTrigger().click {
                                closeKeyfocus()
                                middleware.jobdateClick("jobDate")
                            }
                        }.lparams(matchParent, dip(44)) {
                            topMargin = dip(20)
                        }
                        //jobSkill
                        textView {
                            text = "仕事スキル"
                            textSize = 15f
                            textColorResource = R.color.black33

                        }.lparams(matchParent, dip(21)) {
                            topMargin = dip(16)
                        }

                        userSkill = editText {
                            isVerticalScrollBarEnabled = true
                            isHorizontalScrollBarEnabled = false
                            isHorizontalScrollBarEnabled = false
                            gravity = Gravity.START
                            filters = arrayOf(InputFilter.LengthFilter(50))
                            hint = "スキルを選択してください"
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            backgroundResource = R.drawable.input_border
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
                        }.lparams(width = matchParent, height = dip(65)) {
                            topMargin = dip(7)
                        }
                        //userskill
                        textView {
                            text = "個人スキル"
                            textSize = 15f
                            textColorResource = R.color.black33

                        }.lparams(width = matchParent, height = dip(21)) {
                            topMargin = dip(16)
                        }

                        jobSkill = editText {
                            isVerticalScrollBarEnabled = true
                            isHorizontalScrollBarEnabled = false
                            gravity = Gravity.START
                            filters = arrayOf(InputFilter.LengthFilter(50))
                            hint = "スキルを選択してください"
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            backgroundResource = R.drawable.input_border
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
                        }.lparams(width = matchParent, height = dip(65)) {
                            topMargin = dip(7)
                        }
                        //user
                        textView {
                            text = "私ができること"
                            textSize = 15f
                            textColorResource = R.color.black33

                        }.lparams(width = matchParent, height = dip(21)) {
                            topMargin = dip(16)
                        }

                        iCanDo = editText {
                            isVerticalScrollBarEnabled = true
                            isHorizontalScrollBarEnabled = false
                            gravity = Gravity.START
                            filters = arrayOf(InputFilter.LengthFilter(2000))
                            hint = "スキルを選択してください"
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            backgroundResource = R.drawable.input_border
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
                        }.lparams(width = matchParent, height = dip(165)) {
                            topMargin = dip(7)
                        }
                        this.withTrigger().click {
                            closeKeyfocus()
                        }
                    }.lparams(matchParent, matchParent) {
                        leftMargin = dip(15)
                        rightMargin = dip(15)
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


    private fun closeKeyfocus(){
        firstName.clearFocus()
        lastName.clearFocus()
        phone.clearFocus()
        email.clearFocus()
        line.clearFocus()
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(activity!!.window.decorView.windowToken, 0)
    }
}