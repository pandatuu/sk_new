package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.example.sk_android.R
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import android.widget.ImageView
import android.net.Uri
import android.text.InputFilter
import android.text.InputType
import com.alibaba.fastjson.JSON
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.mvp.view.activity.jobselect.RecruitInfoShowActivity
import com.example.sk_android.mvp.view.activity.register.PersonInformationTwoActivity
import com.example.sk_android.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.radion_gender.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import retrofit2.adapter.rxjava2.HttpException
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
    var person = Person(myAttributes, "", "", "", "", "", "", "", "", "", "", "", "", "","")
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    private lateinit var myDialog: MyDialog

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
        val builder = MyDialog.Builder(activity!!)
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.btnMan -> gender = "MALE"
                R.id.btnWoman -> gender = "FEMALE"
            }
        }

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
            scrollView {
                verticalLayout {
                    backgroundColorResource = R.color.whiteFF
                    orientation = LinearLayout.VERTICAL
                    leftPadding = dip(15)
                    rightPadding = dip(15)
                    bottomPadding = dip(38)

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
                            imageResource = R.mipmap.ico_head
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
                        }.lparams(width = dip(110), height = matchParent) {
                        }

                        linearLayout {
                            surName = editText {
                                backgroundColorResource = R.color.whiteFF
                                hintResource = R.string.IiSurname
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                singleLine = true
                            }.lparams(width = matchParent, height = matchParent) {
                                weight = 1f
                                rightMargin = dip(10)
                            }

                            name = editText {
                                backgroundColorResource = R.color.whiteFF
                                hintResource = R.string.IiNameHint
                                hintTextColor = Color.parseColor("#B3B3B3")
                                textSize = 15f
                                singleLine = true
                            }.lparams(width = matchParent, height = matchParent) {
                                weight = 1f
                            }
                        }.lparams(width = wrapContent, height = matchParent) {
                            weight = 1f
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
                            addView(view)
                        }.lparams(width = wrapContent, height = matchParent) {
                            weight = 1f
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
                            singleLine = true
                            hintResource = R.string.IiPhoneHint
                            hintTextColor = Color.parseColor("#B3B3B3")
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

                    dateInput01LinearLayout = linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.IiBorn
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent) {
                        }
                        dateInput01 = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.IiBornHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            isFocusableInTouchMode = false
                            setOnClickListener { showYearMonthDayPicker() }
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                    }.lparams(width = matchParent, height = dip(44)) {
                        topMargin = dip(20)
                    }

                    dateInputLinearLayout = linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.IiInitialInauguration
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent) {
                        }
                        dateInput = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.IiInitialInaugurationHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                            isFocusableInTouchMode = false
                            setOnClickListener { showYearMonthPicker() }
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                        }
                    }.lparams(width = matchParent, height = dip(44)) {
                        topMargin = dip(20)
                    }

                    statusLinearLayout = linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.IiEmploymentStatu
                            textColorResource = R.color.black33
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent) {
                        }
                        status = editText {
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
                        onClick { submit() }

                    }.lparams(width = matchParent, height = dip(47)) {
                        topMargin = dip(20)
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
        var bornDate = tool.getEditText(dateInput01)
        var myDate = tool.getEditText(dateInput)
        var myStatu = tool.getEditText(status)
        var jobSkill = tool.getEditText(workSkillEdit)
        var userSkill = tool.getEditText(personSkillEdit)
        myName = mySurName + " " + firstName

        var pattern: Pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        var matcher: Matcher = pattern.matcher(myEmail)

        if (mySurName == "" || firstName == "") {
            surNameLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            surNameLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }


        if (myPhone == "") {
            phoneLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            phoneLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }


        if (myEmail == "" || !matcher.matches()) {
            emailLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            emailLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }


        if (myDate == "") {
            dateInputLinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            dateInputLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            var date = stringToLong(myDate,"yyyy-MM")
            person.workingStartDate = date.toString()
        }


        if (bornDate == "") {
            dateInput01LinearLayout.backgroundResource = R.drawable.edit_text_empty
        } else {
            dateInput01LinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            var firstDate = stringToLong(bornDate,"yyyy-MM-dd")
            person.birthday = firstDate.toString()
        }

        if(myDate !="" && bornDate != "" && stringToLong(myDate,"yyyy-MM") > stringToLong(bornDate,"yyyy-MM-dd")){
            dateInputLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            dateInput01LinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }else{
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
            var imageUrl = myUrl!!.get("url").toString()
            imageUrl = imageUrl.substring(1, imageUrl.length - 1)
            person.avatarUrl = imageUrl
        }

        person.displayName = myName
        person.email = myEmail
        person.firstName = firstName
        person.gender = gender
        person.lastName = mySurName
        person.phone = myPhone
        person.gender = gender

        when (myStatu) {
            this.getString(R.string.IiStatusOne) -> jobStatu = "OTHER"
            this.getString(R.string.IiStatusTwo) -> jobStatu = "ON_NEXT_MONTH"
            this.getString(R.string.IiStatusThree) -> jobStatu = "ON_CONSIDERING"
            this.getString(R.string.IiStatusFour) -> jobStatu = "OFF"
        }


        var myAttribute = mapOf<String, String>(
            "jobSkill" to jobSkill.trim(),
            "userSkill" to userSkill.trim()
        )

        person.attributes = myAttribute


        if (mySurName != "" && firstName != "" && myPhone != "" && myEmail != "" && myDate != "" && bornDate != ""
            && myStatu != "" && matcher.matches() && stringToLong(myDate,"yyyy-MM") > stringToLong(bornDate,"yyyy-MM-dd")
        ) {
            myDialog.show()


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
                "workingStartDate" to person.workingStartDate
            )

            val statuParams = mapOf(
                "attributes" to {},
                "state" to jobStatu
            )

            val statuJson = JSON.toJSONString(statuParams)
            val userJson = JSON.toJSONString(params)

            val userBody = RequestBody.create(json, userJson)
            val statusBody = RequestBody.create(json, statuJson)

            var retrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.userUrl))
            retrofitUils.create(RegisterApi::class.java)
                .perfectPerson(userBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    println(it)
                    println("创建结果")
                    if(it.code() in 200..299){
                        retrofitUils.create(RegisterApi::class.java)
                            .UpdateWorkStatu(statusBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                            .subscribe({
                                if(it.code() in 200..299){
                                    myDialog.dismiss()
                                    println("创建工作状态成功")
                                    startActivity<PersonInformationTwoActivity>()
                                }else{
                                    myDialog.dismiss()
                                    println("创建工作状态失败！！")
                                    println(it)
                                    toast("创建工作状态失败！！")
                                }
                            }, {
                                myDialog.dismiss()
                            })
                    }

                    else if(it.code() == 409){
                        myDialog.dismiss()
                        emailLinearLayout.backgroundResource = R.drawable.edit_text_empty
                        phoneLinearLayout.backgroundResource = R.drawable.edit_text_empty
                        toast("电话或者邮箱已注册，请检查！！")
                    }else{
                        myDialog.dismiss()
                        toast("创建失败，请稍后重试！！")
                    }


                },{
                    myDialog.dismiss()
                })
        }
    }


    interface Middleware {

        fun addListFragment()

        fun addImage()
    }

    private fun showYearMonthDayPicker() {
        BasisTimesUtils.showDatePickerDialog(
            context,
            BasisTimesUtils.THEME_HOLO_LIGHT,
            "请选择年月日",
            2001,
            1,
            1,
            object : BasisTimesUtils.OnDatePickerListener {

                override fun onConfirm(year: Int, month: Int, dayOfMonth: Int) {
                    toast("$year-$month-$dayOfMonth")
                    var time = "$year-$month-$dayOfMonth"
                    dateInput01.setText(time)
                }

                override fun onCancel() {

                }
            })
    }

    /**
     * 年月选择
     */
    private fun showYearMonthPicker() {
        BasisTimesUtils.showDatePickerDialog(context, true, "", 2015, 12, 22,
            object : BasisTimesUtils.OnDatePickerListener {

                override fun onConfirm(year: Int, month: Int, dayOfMonth: Int) {
                    dateInput.setText("$year-$month")
                }

                override fun onCancel() {
                    toast("cancle")
                }
            }).setDayGone()
    }

    fun setData(abc: String) {
        status.setText(abc)
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

}


