package com.example.sk_android.mvp.view.fragment.onlineresume

import android.annotation.SuppressLint
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
import com.example.sk_android.custom.layout.floatOnKeyboardLayout
import com.example.sk_android.mvp.model.onlineresume.basicinformation.Sex
import com.example.sk_android.mvp.model.onlineresume.basicinformation.UserBasicInformation
import com.example.sk_android.utils.roundImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onCheckedChange
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onScrollChange
import org.jetbrains.anko.support.v4.UI
import withTrigger
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

            return EditBasicInformation()
        }

    }

    private val chooseList = mutableListOf("自定する", "黙認")
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
    private var isDefault = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        middleware = activity as Middleware
        val fragmentView = createView()
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
        isDefault = uri.isBlank()
        sexValue = info.gender
        if(info.gender == Sex.MALE){
            //加载网络图片
            interPic(info.avatarURL, R.mipmap.person_man)
        }else{
            //加载网络图片
            interPic(info.avatarURL, R.mipmap.person_woman)
        }

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
        isDefault = false
        uri = url
        if(sexValue == Sex.MALE){
            //加载网络图片
            interPic(url, R.mipmap.person_man)
        }else{
            //加载网络图片
            interPic(url, R.mipmap.person_woman)
        }
    }

    fun setDefaultImg() {
        isDefault = true
        if(sexValue == Sex.MALE){
            //加载网络图片
            image.setImageResource(R.mipmap.person_man)
        }else{
            //加载网络图片
            image.setImageResource(R.mipmap.person_woman)
        }
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

        //验证名字非空 (line可空)
        if (firstName.isBlank() || lastName.isBlank()) {
            val toast = Toast.makeText(activity!!.applicationContext, "名前を入力してください", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        // 验证就职日期大于出生日期
        val start = stringToLong(birthDate.text.toString().trim())
        val end = stringToLong(jobDate.text.toString().trim())
        if (end < start) {
            val toast = Toast.makeText(activity!!.applicationContext, "就職日を生年月日より後に設定してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        //验证工作技能非空
        if (workSkill.isBlank()) {
            val toast = Toast.makeText(activity!!.applicationContext, "能力・スキルを入力してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        //验证个人技能非空
        if (personSkill.isBlank()) {
            val toast = Toast.makeText(activity!!.applicationContext, "特技を入力してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }

        //验证我能做的非空
        if (todo.isBlank()) {
            val toast = Toast.makeText(activity!!.applicationContext, "自己PRを入力してください。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
            return null
        }
        // 验证我能做的不超过2000字
        if (todo.length !in 2..2000 && todo != "") {
            val toast = Toast.makeText(activity!!.applicationContext, "2000文字を超えました。", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
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


    @SuppressLint("SetTextI18n", "RtlHardcoded")
    private fun createView(): View? {
        return UI {
            floatOnKeyboardLayout {
                linearLayout {
                    scrollView {
                        isVerticalScrollBarEnabled = false
                        overScrollMode = View.OVER_SCROLL_NEVER
                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            relativeLayout {
                                image = roundImageView {
                                    imageResource = R.mipmap.ico_head
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

                                linearLayout {
                                    lastName = editText {
                                        background = null
                                        hint = "苗字"
                                        hintTextColor = Color.parseColor("#B3B3B3")
                                        textSize = 15f
                                        singleLine = true
                                        gravity = Gravity.RIGHT
                                    }.lparams(matchParent, matchParent) {
                                        weight = 1f
                                        leftMargin = dip(5)
                                    }
                                    firstName = editText {
                                        background = null
                                        hint = "名前"
                                        hintTextColor = Color.parseColor("#B3B3B3")
                                        textSize = 15f
                                        singleLine = true
                                        gravity = Gravity.RIGHT
                                    }.lparams(matchParent, matchParent) {
                                        weight = 1f
                                        leftMargin = dip(5)
                                    }
                                }.lparams(wrapContent, matchParent) {
                                    weight = 1f
                                    rightMargin = dip(30)
                                }
                            }.lparams(matchParent, dip(44))
                            //性别
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                backgroundResource = R.drawable.input_border
                                textView {
                                    text = "性別"
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
                                                    sexValue = Sex.MALE//加载网络图片
                                                    if(!isDefault){
                                                        interPic(uri, R.mipmap.person_man)
                                                    }else{
                                                        image.setImageResource(R.mipmap.person_man)
                                                    }
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
                                                    sexValue = Sex.FEMALE//加载网络图片
                                                    if(!isDefault){
                                                        interPic(uri, R.mipmap.person_woman)
                                                    }else{
                                                        image.setImageResource(R.mipmap.person_woman)
                                                    }
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
                                    gravity = Gravity.RIGHT
                                }.lparams(dip(100), wrapContent) {
                                    rightMargin = dip(30)
                                    alignParentRight()
                                }
                            }.lparams(matchParent, dip(44)) {
                                topMargin = dip(20)
                            }
                            //email
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                backgroundResource = R.drawable.input_border
                                textView {
                                    text = "メールアドレス"
                                    textColorResource = R.color.black33
                                    textSize = 15f
                                    gravity = Gravity.CENTER_VERTICAL
                                }.lparams(wrapContent, matchParent) {
                                    weight = 1f
                                }
                                email = editText {
                                    background = null
                                    singleLine = true
                                    inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                                    textSize = 15f
                                    isEnabled = false
                                    gravity = Gravity.RIGHT
                                }.lparams(wrapContent, wrapContent) {
                                    gravity = Gravity.RIGHT
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
                                    hint = "Line番号を入力してください"
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    textSize = 15f
                                    singleLine = true
                                    gravity = Gravity.RIGHT
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
                                text = "能力・スキル"
                                textSize = 15f
                                textColorResource = R.color.black33

                            }.lparams(matchParent, dip(21)) {
                                topMargin = dip(16)
                            }

                            jobSkill = editText {
                                isVerticalScrollBarEnabled = true
                                isHorizontalScrollBarEnabled = false
                                isHorizontalScrollBarEnabled = false
                                gravity = Gravity.START
                                filters = arrayOf(InputFilter.LengthFilter(50))
                                hint = "能力・スキルを入力してください。"
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                backgroundResource = R.drawable.input_border
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
                            }.lparams(width = matchParent, height = dip(65)) {
                                topMargin = dip(7)
                            }
                            //userskill
                            textView {
                                text = "特技"
                                textSize = 15f
                                textColorResource = R.color.black33

                            }.lparams(width = matchParent, height = dip(21)) {
                                topMargin = dip(16)
                            }

                            userSkill = editText {
                                isVerticalScrollBarEnabled = true
                                isHorizontalScrollBarEnabled = false
                                gravity = Gravity.START
                                filters = arrayOf(InputFilter.LengthFilter(50))
                                hint = "特技を入力してください。"
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                backgroundResource = R.drawable.input_border
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
                            }.lparams(width = matchParent, height = dip(65)) {
                                topMargin = dip(7)
                            }
                            //user
                            textView {
                                text = "自己PR"
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
                                hint = "自己PRを入力してください。"
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                backgroundResource = R.drawable.input_border
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
                        onScrollChange { _, _, _, _, _ ->
                            val imm =
                                activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view.windowToken, 0)
                        }
                        onClick {
                            closeKeyfocus()
                        }
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

    //获取网络图片
    private fun interPic(url: String, avatar: Int) {
        Glide.with(this)
            .load(url)
            .error(avatar)
            .into(image)
    }


    private fun closeKeyfocus() {
        firstName.clearFocus()
        lastName.clearFocus()
        line.clearFocus()
        userSkill.clearFocus()
        jobSkill.clearFocus()
        iCanDo.clearFocus()
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.hideSoftInputFromWindow(activity?.window?.decorView?.windowToken, 0)
    }
}