package com.example.sk_android.mvp.view.fragment.person

import android.annotation.SuppressLint
import android.content.Context
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
import android.text.SpannableStringBuilder
import click
import com.alibaba.fastjson.JSON
import com.bumptech.glide.Glide
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.mvp.view.activity.person.PersonSetActivity
import com.example.sk_android.mvp.view.fragment.register.RegisterApi
import com.example.sk_android.utils.*
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.radion_gender.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import withTrigger
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.HashMap

class PiMainBodyFragment  : Fragment(){
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
    lateinit var status:EditText
    lateinit var statusLinearLayout: LinearLayout
    lateinit var workSkillEdit:EditText
    lateinit var personSkillEdit:EditText
    lateinit var tool: BaseTool
    var gender = "MALE"
    lateinit var headImageView: ImageView
    lateinit var middleware: Middleware
    var jobStatu:String = ""
    private var ImagePaths = HashMap<String, Uri>()
    var myName:String = ""
    var myAttributes = mapOf<String,String>()
    var person = Person(myAttributes,"","","","","","","","","","","","","","")
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    var condition:Int = 0
    lateinit var dateUtil:DateUtil
    private lateinit var myDialog: MyDialog
    var imageUrl = ""

    companion object {
        fun newInstance(result: HashMap<String, Uri>,condition:Int): PiMainBodyFragment {
            val fragment = PiMainBodyFragment()
            fragment.ImagePaths = result
            fragment.condition = condition
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val builder = MyDialog.Builder(activity!!)
            .setCancelable(false)
            .setCancelOutside(false)
        myDialog = builder.create()

        mContext = activity
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
        dateUtil = DateUtil()
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
                            this.withTrigger().click  { middleware.addImage() }
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
                                gravity = Gravity.RIGHT
                            }.lparams(width = matchParent, height = matchParent) {
                                weight = 1f
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
                            }
                        }.lparams(width = wrapContent, height = matchParent) {
                            weight = 1f
                            rightMargin = dip(15)
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
                            singleLine = true
                            hintResource = R.string.IiPhoneHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            inputType = InputType.TYPE_CLASS_PHONE
                            filters = arrayOf(InputFilter.LengthFilter(11))
                            textSize = 15f
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                            rightMargin = dip(15)
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
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                            rightMargin = dip(15)
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
                            onClick {
                                middleware.birthdate()
                            }
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                            rightMargin = dip(15)
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
                            gravity = Gravity.RIGHT
                        }.lparams(width = matchParent, height = wrapContent) {
                            weight = 1f
                            rightMargin = dip(15)
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
                        padding = dip(10)
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
                        padding = dip(10)
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
        myDialog.show()

        var mySurName = tool.getEditText(surName)
        var firstName = tool.getEditText(name)
        var myPhone = tool.getEditText(phone)
        var myEmail = tool.getEditText(email)
        var bornDate = tool.getEditText(dateInput01)
        var myDate = tool.getEditText(dateInput)
        var myStatu = tool.getEditText(status)
        var workSkills = tool.getEditText(workSkillEdit)
        var personSkills = tool.getEditText(personSkillEdit)
        myName = mySurName + firstName

        var pattern: Pattern = Pattern.compile("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        var matcher: Matcher = pattern.matcher(myEmail)

        if (mySurName == "" || firstName == "") {
            surNameLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else {
            surNameLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }


        if(myPhone == ""){
            phoneLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else{
            phoneLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }


        if(myEmail == "" || !matcher.matches()){
            emailLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else {
            emailLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }


        if(myDate == ""){
            dateInputLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else {
            dateInputLinearLayout.backgroundResource = R.drawable.edit_text_no_empty

            var date = DateUtil.stringShortToMillisecond(myDate).toString()
            person.workingStartDate = date
        }


        if(bornDate == ""){
            dateInput01LinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else {
            dateInput01LinearLayout.backgroundResource = R.drawable.edit_text_no_empty

            var firstDate = stringToLong(bornDate)
            person.birthday = firstDate.toString()
        }


        if(myStatu.isNullOrBlank()){
            statusLinearLayout.backgroundResource = R.drawable.edit_text_empty

        }else {
            statusLinearLayout.backgroundResource = R.drawable.edit_text_no_empty

        }




        //完善信息统一提交，因此只进行本地地址保存，最后在进行图片上传
        if(ImagePaths.get("uri") != null){
            var newImageURI = ImagePaths.get("uri").toString().substring(7)
            var myUrl = UploadPic().upLoadPic(newImageURI,mContext!!,"user-head")
            var newImageUrl = myUrl!!.get("url").toString()
            newImageUrl = newImageUrl.substring(1,newImageUrl.length-1)
            person.avatarUrl = newImageUrl
        }else{
            person.avatarUrl = imageUrl
        }

        person.displayName = myName
        person.email = myEmail
        person.firstName = firstName
        person.gender = gender
        person.lastName = mySurName
        person.phone = myPhone
        person.gender = gender

        when(myStatu){
            this.getString(R.string.IiStatusOne) -> jobStatu = "OTHER"
            this.getString(R.string.IiStatusTwo) -> jobStatu = "ON_NEXT_MONTH"
            this.getString(R.string.IiStatusThree) -> jobStatu = "ON_CONSIDERING"
            this.getString(R.string.IiStatusFour) -> jobStatu = "OFF"
        }

        var myAttribute = mapOf<String,String>(
            "jobSkill" to workSkills.trim(),
            "userSkill" to personSkills.trim()
        )

        if(mySurName != "" && firstName != "" && myPhone != "" && myEmail != "" && myDate != "" && bornDate != ""
            && myStatu != "" && matcher.matches()){

            //构造HashMap(个人信息完善)
            val params = mapOf(
                "attributes" to myAttribute,
                "avatarUrl" to person.avatarUrl,
                "birthday" to person.birthday,
                "displayName" to person.displayName,
                "email" to person.email,
                "firstName" to person.firstName,
                "gender" to person.gender,
                "lastName" to person.lastName,
                "phone" to person.phone,
                "workingStartDate" to person.workingStartDate,
                "code" to "86"
            )
            val statuParams = mapOf(
                "attributes" to {},
                "state" to jobStatu
            )

            val statuJson = JSON.toJSONString(statuParams)
            val userJson = JSON.toJSONString(params)


            val body = RequestBody.create(json, userJson)
            val statuBody = RequestBody.create(json,statuJson)

            var retrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.userUrl))

            if(condition == 0){
                retrofitUils.create(PersonApi::class.java)
                    .updatePerson(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        println(it)
                        println("123566")
                        if(it.code() in 200..299){
                            retrofitUils.create(RegisterApi::class.java)
                            .UpdateWorkStatu(statuBody)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                            .subscribe({
                                if(it.code() in 200..299){
                                    myDialog.dismiss()
                                    startActivity<PersonSetActivity>()
                                }else{
                                    toast("更新个人状态失败")
                                    myDialog.dismiss()
                                }

                                },{
                                myDialog.dismiss()
                            })
                        }else{
                            toast("更新个人信息失败")
                            myDialog.dismiss()
                        }
                    },{
                        myDialog.dismiss()
                    })
            }else{
                retrofitUils.create(RegisterApi::class.java)
                    .perfectPerson(body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                    .subscribe({
                        if(it.code() in 200..299){
                            retrofitUils.create(RegisterApi::class.java)
                                .UpdateWorkStatu(statuBody)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                                .subscribe({
                                    if(it.code() in 200..299){
                                        myDialog.dismiss()
                                        startActivity<PersonSetActivity>()
                                    }else{
                                        toast("创建个人状态失败")
                                        myDialog.dismiss()
                                    }
                                },{
                                    myDialog.dismiss()
                                })
                        } else {
                            toast("创建个人信息失败！！")
                            myDialog.dismiss()
                        }
                },{
                        myDialog.dismiss()
                    })
            }
        } else {
            myDialog.dismiss()
        }
    }


    interface Middleware {

        fun addListFragment()

        fun addImage()

        fun birthdate()

        fun jobdate()
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
        BasisTimesUtils.showDatePickerDialog(context, true, "请选择年月", 2015, 12, 22,
            object : BasisTimesUtils.OnDatePickerListener {

                override fun onConfirm(year: Int, month: Int, dayOfMonth: Int) {
                    dateInput.setText("$year-$month")
                }

                override fun onCancel() {
                    toast("cancle")
                }
            }).setDayGone()
    }

    fun setData(abc:String){
        status.setText(abc)
    }


    fun setImage(imageUri:Uri){
        headImageView.setImageURI(imageUri)
    }

    fun ininData(person:JsonObject){
        myDialog.show()
        try{




            var mySurName= ""
            var myName = ""
            var myPhone = ""
            var myEmail = ""
            var myBorn = ""
            var myGender = ""
            var myWork = ""
            var myJobSkill = ""
            var myUserSkill = ""

            var statu = person.get("auditState").toString().replace("\"","")
            if(statu.equals("PENDING")){
                imageUrl = person.get("changedContent").asJsonObject.get("avatarURL").toString().replace("\"","").split(";")[0]
                mySurName = person.get("changedContent").asJsonObject.get("lastName").toString().replace("\"","")
                myName = person.get("changedContent").asJsonObject.get("firstName").toString().replace("\"","")
                myPhone = person.get("changedContent").asJsonObject.get("phone").toString().replace("\"","")
                myEmail = person.get("changedContent").asJsonObject.get("email").toString().replace("\"","")
                myGender = person.get("changedContent").asJsonObject.get("gender").toString().replace("\"","")
                myBorn = person.get("changedContent").asJsonObject.get("birthday").toString().replace("\"","")
                myWork = person.get("changedContent").asJsonObject.get("workingStartDate").toString().replace("\"","")
                myJobSkill = person.get("changedContent").asJsonObject.get("attributes").asJsonObject.get("jobSkill").toString().replace("\"","")
                myUserSkill = person.get("changedContent").asJsonObject.get("attributes").asJsonObject.get("userSkill").toString().replace("\"","")
            }else{
                imageUrl = person.get("avatarURL").toString().replace("\"","").split(";")[0]
                mySurName = person.get("lastName").toString().replace("\"","")
                myName = person.get("firstName").toString().replace("\"","")
                myPhone = person.get("phone").toString().replace("\"","")
                myEmail = person.get("email").toString().replace("\"","")
                myGender = person.get("gender").toString().replace("\"","")
                myBorn = person.get("birthday").toString().replace("\"","")
                myWork = person.get("workingStartDate").toString().replace("\"","")

                if(person.get("attributes")!=null  &&  person.get("attributes").asJsonObject.get("jobSkill")!=null){
                    myJobSkill = person.get("attributes").asJsonObject.get("jobSkill").toString().replace("\"","")
                }
                if( person.get("attributes")!=null  && person.get("attributes").asJsonObject.get("userSkill")!=null){
                    myUserSkill = person.get("attributes").asJsonObject.get("userSkill").toString().replace("\"","")
                }
            }

            if(imageUrl!=null  && !"".equals(imageUrl)){
                Glide.with(this)
                    .asBitmap()
                    .load(imageUrl)
                    .placeholder(R.mipmap.ico_head)
                    .into(headImageView)
            }
            surName.setText(mySurName)
            name.setText(myName)
            phone.setText(myPhone)
            email.setText(myEmail)
            var gender = myGender
            when(gender){
                "MALE" -> radioGroup.check(R.id.btnWoman)
                "FEMALE" -> radioGroup.check(R.id.btnMan)
            }

            var born = myBorn
            dateInput01.setText(longToString(born.toLong()))

            var work = myWork
            dateInput.setText(DateUtil.millisecondToStringShort(work.toLong()))

            var workSkill = myJobSkill
            workSkillEdit.setText(workSkill)

            var personSkill = myUserSkill
            personSkillEdit.setText(personSkill)

        }catch (e:Exception){
            println("出错了!")
            e.printStackTrace()
            println(e.message)
        }


        if (myDialog != null) {
            if (myDialog!!.isShowing()) {
                myDialog!!.dismiss()

            }
        }
    }

    fun initStatu(statuNumber:JsonObject){
        var statu = this.getString(R.string.IiStatusOne)
        var workStatu = statuNumber.get("state").toString().replace("\"","")
        println("*****************************3")
        println(workStatu)
        when(workStatu){
            "OTHER" -> statu = this.getString(R.string.IiStatusOne)
            "ON_NEXT_MONTH"-> statu = this.getString(R.string.IiStatusTwo)
            "ON_CONSIDERING" -> statu = this.getString(R.string.IiStatusThree)
            "OFF"-> statu = this.getString(R.string.IiStatusFour)
        }
        status.setText(statu)
    }

    fun setbirthDate(text: String){
        dateInput01.text = SpannableStringBuilder(text)
    }

    fun setjobDate(text: String){
        dateInput.text = SpannableStringBuilder(text)
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

}


