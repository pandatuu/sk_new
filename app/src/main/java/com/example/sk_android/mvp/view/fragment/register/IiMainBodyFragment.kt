package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.net.Uri
import android.os.Handler
import android.preference.PreferenceManager
import android.text.InputFilter
import android.text.InputType
import android.view.inputmethod.InputMethodManager
import android.widget.*
import click
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.custom.layout.floatOnKeyboardLayout
import com.example.sk_android.mvp.api.person.User
import com.example.sk_android.mvp.application.App
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.mvp.view.activity.register.PersonInformationTwoActivity
import com.example.sk_android.utils.*
import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.radion_gender.*
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.rx2.awaitSingle
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import org.json.JSONObject
import retrofit2.adapter.rxjava2.HttpException
import withTrigger
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class IiMainBodyFragment : Fragment() {
    private var mContext: Context? = null
    lateinit var dateInput: EditText
    lateinit var dateInputLinearLayout: LinearLayout
    lateinit var dateInput01: EditText
    lateinit var dateInput01LinearLayout: LinearLayout
    lateinit var surName: EditText
    lateinit var surNameLinearLayout: LinearLayout
    lateinit var name: EditText
    lateinit var phone: EditText
    lateinit var phoneLinearLayout: LinearLayout
    lateinit var email: EditText
    lateinit var emailLinearLayout: LinearLayout
    lateinit var brahma: EditText
    lateinit var brahmaLinearLayout: LinearLayout
    lateinit var status: EditText
    lateinit var statusLinearLayout: LinearLayout
    lateinit var workSkillEdit: EditText
    lateinit var personSkillEdit: EditText
    lateinit var tool: BaseTool
    var gender = "MALE"
    lateinit var headImageView: ImageView
    lateinit var middleware: Middleware
    var jobStatu: String = ""
    private var ImagePaths = HashMap<String, Uri>()
    var myName: String = ""
    var myAttributes = mapOf<String, Serializable>()
    var person = Person(myAttributes, "", "", "", "", "", "", "", "", "", "", "", "", "", "")
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!){
            val toast = Toast.makeText(activity, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }
    lateinit var ms: SharedPreferences
    lateinit var myInformation :SharedPreferences
    var total = 0
    var defaultPhone = ""
    var defaultCountry = ""

    companion object {
        fun newInstance(result: HashMap<String, Uri>): IiMainBodyFragment {
            val fragment = IiMainBodyFragment()
            fragment.ImagePaths = result
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = activity

        myInformation = PreferenceManager.getDefaultSharedPreferences(context)
        defaultPhone = myInformation.getString("phone",this.getString(R.string.IiPhoneHint))
        defaultCountry = myInformation.getString("country","81")
        ms = PreferenceManager.getDefaultSharedPreferences(mContext)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.btnMan -> {gender = "MALE"
                    setHead(R.id.btnMan)}
                R.id.btnWoman -> {gender = "FEMALE"
                    setHead(R.id.btnWoman)}
            }
        }
        phone.setText(defaultPhone)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val fragmentView = createView()
        middleware = activity as Middleware
        return fragmentView
    }

    @SuppressLint("RtlHardcoded")
    fun createView(): View {
        tool = BaseTool()
        val view = View.inflate(mContext, R.layout.radion_gender, null)
        return UI {
            floatOnKeyboardLayout {
                scrollView {
                    verticalLayout {
                        backgroundColorResource = R.color.whiteFF
                        orientation = LinearLayout.VERTICAL
                        leftPadding = dip(15)
                        rightPadding = dip(15)
                        bottomPadding = dip(38)

                        onClick {
                            closeKeyfocus()
                        }

                        textView {
                            textResource = R.string.IiIntroduction
                            textSize = 18f
                            gravity = Gravity.LEFT
                            textColorResource = R.color.black33
                        }.lparams(width = matchParent, height = dip(25)) {
                            topMargin = dip(20)
                        }

                        linearLayout {
                            gravity = Gravity.CENTER

                            headImageView = roundImageView {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                imageResource = R.mipmap.person_man
                                setOnClickListener { middleware.addImage() }
                            }.lparams(width = dip(90), height = dip(90)) {}


                        }.lparams(width = matchParent, height = dip(145)) {}


                        surNameLinearLayout = linearLayout {
                            backgroundResource = R.drawable.input_border
                            textView {
                                textResource = R.string.IiName
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(width = wrapContent, height = matchParent) {
                            }

                            linearLayout {
                                surName = editText {
                                    backgroundColorResource = R.color.whiteFF
                                    hintResource = R.string.IiSurname
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    textSize = 15f
                                    singleLine = true
                                    gravity = Gravity.RIGHT
                                }.lparams(width = matchParent, height = matchParent) {
                                    weight = 1f
                                    leftMargin = dip(5)
                                }

                                name = editText {
                                    backgroundColorResource = R.color.whiteFF
                                    hintResource = R.string.IiNameHint
                                    hintTextColor = Color.parseColor("#B3B3B3")
                                    textSize = 15f
                                    singleLine = true
                                    gravity = Gravity.RIGHT
                                }.lparams(width = matchParent, height = matchParent) {
                                    weight = 1f
                                    leftMargin = dip(5)
                                }
                            }.lparams(width = wrapContent, height = matchParent) {
                                weight = 1f
                                rightMargin = dip(15)
                                leftMargin = dip(5)
                            }
                        }.lparams(width = matchParent, height = dip(44)) {}

                        linearLayout {
                            backgroundResource = R.drawable.input_border
                            textView {
                                textResource = R.string.IiGender
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(width = dip(110), height = matchParent) {
                            }
                            linearLayout {
                                gravity = Gravity.CENTER_VERTICAL
                                relativeLayout {
                                    gravity = Gravity.RIGHT
                                    addView(view)
                                }.lparams(matchParent, wrapContent)
                            }.lparams(width = wrapContent, height = matchParent) {
                                weight = 1f
                                rightMargin = dip(15)
                            }
                        }.lparams(width = matchParent, height = dip(44)) {
                            topMargin = dip(20)
                        }

                        phoneLinearLayout = linearLayout {
                            backgroundResource = R.drawable.input_border
                            textView {
                                textResource = R.string.IiPhone
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(width = dip(110), height = matchParent) {
                            }
                            phone = editText {
                                backgroundColorResource = R.color.whiteFF
                                gravity = Gravity.RIGHT
                                singleLine = true
                                inputType = InputType.TYPE_CLASS_PHONE
                                filters = arrayOf(InputFilter.LengthFilter(11))
                                textSize = 15f
                            }.lparams(width = matchParent, height = wrapContent) {
                                weight = 1f
                            }
                        }.lparams(width = matchParent, height = dip(44)) {
                            topMargin = dip(20)
                        }

                        emailLinearLayout = linearLayout {
                            backgroundResource = R.drawable.input_border
                            textView {
                                textResource = R.string.IiMail
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL

                            }.lparams(width = dip(110), height = matchParent) {
                            }
                            email = editText {
                                backgroundColorResource = R.color.whiteFF
                                gravity = Gravity.RIGHT
                                singleLine = true
                                hintResource = R.string.IiMailHint
                                hintTextColor = Color.parseColor("#B3B3B3")
                                inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                                textSize = 15f
                            }.lparams(width = matchParent, height = wrapContent) {
                                weight = 1f
                            }
                        }.lparams(width = matchParent, height = dip(44)) {
                            topMargin = dip(20)
                        }

                        brahmaLinearLayout = linearLayout {
                            backgroundResource = R.drawable.input_border
                            textView {
                                textResource = R.string.IiBrahma
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL

                            }.lparams(width = dip(110), height = matchParent) {
                            }
                            brahma = editText {
                                backgroundColorResource = R.color.whiteFF
                                gravity = Gravity.RIGHT
                                singleLine = true
                                hintResource = R.string.IiBrahmaHint
                                hintTextColor = Color.parseColor("#B3B3B3")
                                inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                                textSize = 15f
                            }.lparams(width = matchParent, height = wrapContent) {
                                weight = 1f
                            }
                        }.lparams(width = matchParent, height = dip(44)) {
                            topMargin = dip(20)
                        }

                        dateInput01LinearLayout = linearLayout {
                            backgroundResource = R.drawable.input_border
                            gravity = Gravity.CENTER_VERTICAL
                            textView {
                                textResource = R.string.IiBorn
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(width = dip(110), height = matchParent) {
                            }
                            dateInput01 = editText {
                                gravity = Gravity.RIGHT
                                backgroundColorResource = R.color.whiteFF
                                singleLine = true
                                hintResource = R.string.IiBornHint
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                isFocusableInTouchMode = false
                                setOnClickListener { middleware.birthdate() }
                            }.lparams(width = matchParent, height = wrapContent) {
                                weight = 1f
                            }

                            imageView {
                                imageResource = R.mipmap.register_select_nor
                            }.lparams(width = dip(8), height = dip(15)) {
                                leftMargin = dip(10)
                            }
                        }.lparams(width = matchParent, height = dip(44)) {
                            topMargin = dip(20)
                        }

                        dateInputLinearLayout = linearLayout {
                            backgroundResource = R.drawable.input_border
                            gravity = Gravity.CENTER_VERTICAL
                            textView {
                                textResource = R.string.IiInitialInauguration
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(width = dip(110), height = matchParent) {
                            }
                            dateInput = editText {
                                gravity = Gravity.RIGHT
                                backgroundColorResource = R.color.whiteFF
                                singleLine = true
                                hintResource = R.string.IiInitialInaugurationHint
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                isFocusableInTouchMode = false
                                setOnClickListener { middleware.twoOnClick() }
                            }.lparams(width = matchParent, height = wrapContent) {
                                weight = 1f
                            }

                            imageView {
                                imageResource = R.mipmap.register_select_nor
                            }.lparams(width = dip(8), height = dip(15)) {
                                leftMargin = dip(10)
                            }
                        }.lparams(width = matchParent, height = dip(44)) {
                            topMargin = dip(20)
                        }

                        statusLinearLayout = linearLayout {
                            backgroundResource = R.drawable.input_border
                            gravity = Gravity.CENTER_VERTICAL
                            textView {
                                textResource = R.string.IiEmploymentStatu
                                textColorResource = R.color.black33
                                textSize = 15f
                                gravity = Gravity.CENTER_VERTICAL
                            }.lparams(width = dip(110), height = matchParent) {
                            }
                            status = editText {
                                gravity = Gravity.RIGHT
                                backgroundColorResource = R.color.whiteFF
                                singleLine = true
                                hintResource = R.string.IiEmploymentStatuHint
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                isFocusableInTouchMode = false
                                setOnClickListener { middleware.addListFragment() }
                            }.lparams(width = matchParent, height = wrapContent) {
                                weight = 1f
                            }

                            imageView {
                                imageResource = R.mipmap.register_select_nor
                            }.lparams(width = dip(8), height = dip(15)) {
                                leftMargin = dip(10)
                            }

                        }.lparams(width = matchParent, height = dip(44)) {
                            topMargin = dip(20)
                        }

                        textView {
                            textResource = R.string.IiWorkSkills
                            textSize = 15f
                            textColorResource = R.color.black33

                        }.lparams(width = matchParent, height = dip(21)) {
                            topMargin = dip(16)
                        }

                        workSkillEdit = editText {
                            backgroundColorResource = R.color.whiteFF
                            isVerticalScrollBarEnabled = true
                            isHorizontalScrollBarEnabled = false
                            gravity = Gravity.TOP
//                        inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                            filters = arrayOf(InputFilter.LengthFilter(50))
                            minLines = 3
                            maxLines = 5
                            hintResource = R.string.IiWorkSkillsHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            backgroundResource = R.drawable.input_border
                        }.lparams(width = matchParent, height = dip(65)) {
                            topMargin = dip(7)
                        }

                        textView {
                            textResource = R.string.IiPersonalSkills
                            textSize = 15f
                            textColorResource = R.color.black33

                        }.lparams(width = matchParent, height = dip(21)) {
                            topMargin = dip(16)
                        }

                        personSkillEdit = editText {
                            isVerticalScrollBarEnabled = true
                            isHorizontalScrollBarEnabled = false
                            gravity = Gravity.TOP
                            filters = arrayOf(InputFilter.LengthFilter(50))
                            hintResource = R.string.IiPersonalSkillsHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            backgroundResource = R.drawable.input_border
                        }.lparams(width = matchParent, height = dip(65)) {
                            topMargin = dip(7)
                        }

                        button {
                            textResource = R.string.IiButtonText
                            textSize = 16f
                            textColorResource = R.color.whiteFF
                            gravity = Gravity.CENTER
                            backgroundColorResource = R.color.yellowFFB706
                            this.withTrigger(3000)
                                .click { GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) { submit() } }

                        }.lparams(width = matchParent, height = dip(47)) {
                            topMargin = dip(20)
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

    private suspend fun submit() {

        var mySurName = tool.getEditText(surName)
        var firstName = tool.getEditText(name)
        var myPhone = tool.getEditText(phone)
        var myEmail = tool.getEditText(email)
        var myBrahma = tool.getEditText(brahma)
        var bornDate = tool.getEditText(dateInput01)
        var myDate = tool.getEditText(dateInput)
        var myStatu = tool.getEditText(status)
        var jobSkill = tool.getEditText(workSkillEdit).trim()
        var userSkill = tool.getEditText(personSkillEdit).trim()
        myName = mySurName + firstName
        var rightPhone = "+$defaultCountry"+myPhone
        var result = isPhoneNumberValid(myPhone,rightPhone)

        var pattern: Pattern =
            Pattern.compile("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
        var matcher: Matcher = pattern.matcher(myEmail)

        var patternPhone: Pattern = Pattern.compile("/^(\\+?81|0)\\d{1,4}[ \\-]?\\d{1,4}[ \\-]?\\d{4}\$/")
        var matcherPhone: Matcher = patternPhone.matcher(myPhone)

        if (mySurName == "" || firstName == "") {
            surNameLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            surNameLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }


        if (myPhone == "") {
            phoneLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {

            //        电话判定,测试阶段屏蔽
//        if (!result){
//            phoneLinearLayout.backgroundResource = R.drawable.edit_text_empty
//        }else{
//            phoneLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
//        }
            phoneLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }




        if (myEmail == "") {
            emailLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            emailLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if (myBrahma == "") {
            brahmaLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            brahmaLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if (!matcher.matches() && myEmail != "") {

            var toast = Toast.makeText(mContext, this.getString(R.string.piEmailError), Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
//            toast(this.getString(R.string.piEmailError))
            emailLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }

        if (matcher.matches() && myEmail != "") {
            emailLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }



        if (myDate == "") {
            dateInputLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            dateInputLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            var date = stringToLong(myDate, "yyyy-MM")
            person.workingStartDate = date.toString()
        }


        if (bornDate == "") {
            dateInput01LinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            dateInput01LinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            var firstDate = stringToLong(bornDate, "yyyy-MM-dd")
            person.birthday = firstDate.toString()
        }

        if (myDate != "" && bornDate != "" && stringToLong(myDate, "yyyy-MM") > stringToLong(bornDate, "yyyy-MM-dd")) {
            dateInputLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            dateInput01LinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if (myDate != "" && bornDate != "" && stringToLong(myDate, "yyyy-MM") < stringToLong(bornDate, "yyyy-MM-dd")) {
            toast(this.getString(R.string.endOrStartOne))
            dateInputLinearLayout.backgroundResource = R.drawable.edit_text_empty
            dateInput01LinearLayout.backgroundResource = R.drawable.edit_text_empty
        }



        if (myStatu.isNullOrBlank()) {
            statusLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            statusLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }


        //完善信息统一提交，因此只进行本地地址保存，最后在进行图片上传
        if (ImagePaths.get("uri") != null) {
            var imageURI = ImagePaths.get("uri").toString().substring(7)
            var myUrl = UploadPic().upLoadPic(imageURI, mContext!!, "user-head")
            var imageUrl = myUrl!!.get("url").toString().split(";")[0]
            imageUrl = imageUrl.substring(1)
            person.avatarUrl = imageUrl
        }

        person.displayName = myName
        person.email = myEmail
        person.firstName = firstName
        person.gender = gender
        person.lastName = mySurName
        person.phone = myPhone
        person.gender = gender
        person.line = myBrahma

        when (myStatu) {
            this.getString(R.string.IiStatusOne) -> jobStatu = "OFF"
            this.getString(R.string.IiStatusTwo) -> jobStatu = "ON_NEXT_MONTH"
            this.getString(R.string.IiStatusThree) -> jobStatu = "ON_CONSIDERING"
            this.getString(R.string.IiStatusFour) -> jobStatu = "OTHER"
        }


        var myAttribute = mapOf<String, String>(
            "jobSkill" to jobSkill.trim(),
            "userSkill" to userSkill.trim()
        )

        person.attributes = myAttribute


        if (mySurName != "" && firstName != "" && myPhone != "" && myEmail != "" && myDate != "" && bornDate != ""
            && myStatu != "" && matcher.matches() && stringToLong(myDate, "yyyy-MM") > stringToLong(
                bornDate,
                "yyyy-MM-dd"
            ) && myBrahma != ""
        ) {
            println("11111")
            thisDialog=DialogUtils.showLoading(context!!)
            mHandler.postDelayed(r, 12000)

            val params = mapOf(
                "attributes" to person.attributes,
                "avatarUrl" to person.avatarUrl,
                "birthday" to person.birthday,
                "displayName" to person.displayName,
                "email" to person.email,
                "firstName" to person.firstName,
                "gender" to person.gender,
                "lastName" to person.lastName,
                "phone" to person.phone,
                "line" to person.line,
                "workingStartDate" to person.workingStartDate,
                "code" to defaultCountry
            )

            val statuParams = mapOf(
                "attributes" to {},
                "state" to jobStatu
            )

            val userJson = JSON.toJSONString(params)
            val statuJson = JSON.toJSONString(statuParams)

            val userBody = RequestBody.create(json, userJson)
            val statusBody = RequestBody.create(json, statuJson)
            try{
                var retrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.userUrl))
                val it = retrofitUils.create(RegisterApi::class.java)
                    .perfectPerson(userBody)
                    .subscribeOn(Schedulers.io())
                    .awaitSingle()

                if (it.code() in 200..299) {
                    var reResult = it.body()
                    var reId = reResult!!.substring(reResult.length-6,reResult.length)

                    retrofitUils.create(RegisterApi::class.java)
                        .UpdateWorkStatu(statusBody)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                        .subscribe({
                            if (it.code() in 200..299) {
                                println("创建工作状态成功")

                                val resumeParams = mapOf(
                                    "name" to person.displayName+"_"+reId+this.getString(R.string.IiResumeName),
                                    "isDefault" to true,
                                    "type" to "ONLINE"
                                )
                                val resumeJson = JSON.toJSONString(resumeParams)
                                val resumeBody = RequestBody.create(json, resumeJson)

                                // 创建简历,获取简历ID
                                var jobRetrofitUils = RetrofitUtils(activity!!, this.getString(R.string.jobUrl))
                                jobRetrofitUils.create(RegisterApi::class.java)
                                    .createOnlineResume(resumeBody)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                                    .subscribe({
                                        println("创建个人线上简历成功")
                                        var newResumtId = it

                                        var userRetrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.userUrl))
                                        userRetrofitUils.create(User::class.java)
                                            .getSelfInfo()
                                            .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                                            .subscribe({
                                                var item = JSONObject(it.toString())
                                                println("登录者信息")
                                                println(item.toString())
                                                var mEditor: SharedPreferences.Editor = ms.edit()
                                                mEditor.putString("id", item.getString("id"))
                                                mEditor.putString("avatarURL", item.getString("avatarURL"))
                                                mEditor.putString("name", item.getString("displayName"))
                                                mEditor.putString("gender",item.getString("gender"))
                                                mEditor.commit()

                                                DialogUtils.hideLoading(thisDialog)

                                                var application = App.getInstance()
                                                application!!.initMessage()

                                                application!!.initData()


                                                startActivity<PersonInformationTwoActivity>("resumeId" to newResumtId)
                                                activity!!.finish()
                                                activity!!.overridePendingTransition(R.anim.fade_in_out, R.anim.fade_in_out)

                                            }, {
                                                DialogUtils.hideLoading(thisDialog)
                                            })

                                    },{
                                        toast(this.getString(R.string.IiResumeFail))
                                        DialogUtils.hideLoading(thisDialog)
                                    })

                            } else {
                                DialogUtils.hideLoading(thisDialog)
                                println("创建工作状态失败！！")
                                println(it)
                                println(it.body())
                                toast(this.getString(R.string.IiStateFail))
                            }

                        }, {
                            DialogUtils.hideLoading(thisDialog)
                        })
                }

                if (it.code() == 409) {
                    DialogUtils.hideLoading(thisDialog)
                    emailLinearLayout.backgroundResource = R.drawable.edit_text_empty
                    phoneLinearLayout.backgroundResource = R.drawable.edit_text_empty
                    toast(this.getString(R.string.IiPhoneOrEmailRepeat))
                }

                if (it.code() in 300..199 && it.code() != 409) {
                    DialogUtils.hideLoading(thisDialog)
                    toast(this.getString(R.string.IiCreatedFail))
                }
            }catch (throwable: Throwable){
                toast("未进入")
                DialogUtils.hideLoading(thisDialog)
            }
        }
    }


    interface Middleware {

        fun addListFragment()

        fun addImage()

        fun birthdate()

        fun twoOnClick()
    }


    fun setData(abc: String) {
        status.setText(abc)
    }

    fun setBirthday(result:String){
        dateInput01.setText(result.trim())
    }

    fun setPositionDate(result:String){
        dateInput.setText(result.trim())
    }


    fun setImage(imageUri: Uri) {
        headImageView.setImageURI(imageUri)
    }

    // 类型转换
    private fun longToString(long: Long, format: String): String {
        val str = SimpleDateFormat(format).format(Date(long))
        return str
    }

    // 类型转换
    private fun stringToLong(str: String, format: String): Long {
        val date = SimpleDateFormat(format).parse(str)
        return date.time
    }

    private fun closeKeyfocus(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        surName.clearFocus()
        name.clearFocus()
        phone.clearFocus()
        email.clearFocus()
        brahma.clearFocus()
        workSkillEdit.clearFocus()
        personSkillEdit.clearFocus()
    }

    /**
     * 根据区号判断是否是正确的电话号码
     * @param phoneNumber :带国家码的电话号码
     * @param countryCode :默认国家码
     * return ：true 合法  false：不合法
     */
    fun isPhoneNumberValid(phoneNumber: String, countryCode: String): Boolean {

        println("isPhoneNumberValid: $phoneNumber/$countryCode")
        val phoneUtil = PhoneNumberUtil.getInstance()
        try {
            val numberProto = phoneUtil.parse(phoneNumber, countryCode)
            return phoneUtil.isValidNumber(numberProto)
        } catch (e: NumberParseException) {
            System.err.println("isPhoneNumberValid NumberParseException was thrown: " + e.toString())
        }

        return false
    }

    //选择性别之后，如果头像未赋值，则根据性别更改默认头像
    fun setHead(result:Int){
        println(ImagePaths)

        if(ImagePaths.size == 0){
            if(result == R.id.btnMan){
                headImageView.setImageResource(R.mipmap.person_man)
            }

            if(result == R.id.btnWoman){
                headImageView.setImageResource(R.mipmap.person_woman)
            }
        }
    }

}


