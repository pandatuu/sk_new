package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import anet.channel.util.Utils
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.sk_android.R
import com.example.sk_android.custom.layout.MyDialog
import com.example.sk_android.mvp.model.register.Education
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import com.example.sk_android.mvp.view.activity.register.PersonInformationThreeActivity
import com.example.sk_android.utils.BasisTimesUtils
import com.example.sk_android.utils.DialogUtils
import com.example.sk_android.utils.RetrofitUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


class PtwoMainBodyFragment:Fragment() {
    private var mContext: Context? = null
    lateinit var startStudent:EditText
    lateinit var startLinearLayout: LinearLayout
    lateinit var endStudent:EditText
    lateinit var endLinearLayout: LinearLayout
    lateinit var educationEdit:EditText
    lateinit var educationLinearLayout: LinearLayout
    lateinit var schoolEdit:EditText
    lateinit var schoolLinearLayout: LinearLayout
    lateinit var majorEdit:EditText
    lateinit var majorLinearLayout: LinearLayout
    lateinit var tool: BaseTool
    lateinit var intermediary: Intermediary
    lateinit var name:String
    var json: MediaType? = MediaType.parse("application/json; charset=utf-8")
    var myAttributes = mapOf<String,Serializable>()
    var education = Education(myAttributes,"","","","","","")
    var thisDialog: MyDialog?=null
    var mHandler = Handler()
    var r: Runnable = Runnable {
        //do something
        if (thisDialog?.isShowing!!){
            val toast = Toast.makeText(context, "ネットワークエラー", Toast.LENGTH_SHORT)//网路出现问题
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        DialogUtils.hideLoading(thisDialog)
    }
    var resumeId = ""

    companion object {
        fun newInstance(resumeId:String): PtwoMainBodyFragment {
            val fragment = PtwoMainBodyFragment()
            fragment.resumeId = resumeId
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = activity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var fragmentView=createView()
        intermediary = activity as Intermediary
        return fragmentView
    }

    fun createView():View{
        tool= BaseTool()
        var view = View.inflate(mContext, R.layout.radion_gender, null)
        return UI {

                verticalLayout {
                    backgroundColorResource = R.color.whiteFF
                    orientation = LinearLayout.VERTICAL
                    leftPadding = dip(15)
                    rightPadding = dip(15)

                    onClick {
                        closeKeyfocus()
                    }

                    textView {
                        textResource = R.string.PtwoIntroduction
                        textSize = 18f
                        gravity = Gravity.LEFT
                        textColorResource = R.color.black33
                    }.lparams(width = matchParent, height = dip(25)) {
                        topMargin = dip(20)
                    }


                    schoolLinearLayout = linearLayout {
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.school
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        schoolEdit = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.schoolHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    educationLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.education
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        educationEdit = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            isFocusableInTouchMode = false
                            hintResource = R.string.educationHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            rightPadding = dip(10)
                            textSize = 15f
                            onClick { intermediary.addListFragment() }
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.btn_continue_nor
                            }.lparams(width = matchParent,height = dip(11))
                        }.lparams(width = dip(6),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    majorLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.majorField
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        majorEdit = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            hintResource = R.string.majorFieldHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            rightPadding = dip(10)
                            textSize = 15f
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }


                    startLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.admission
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        startStudent = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            isFocusableInTouchMode = false
                            hintResource = R.string.admissionHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            rightPadding = dip(10)
                            textSize = 15f
                            onClick {
                                intermediary.twoOnClick("start")
                            }
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.register_select_nor
                            }.lparams(width = matchParent,height = dip(15))
                        }.lparams(width = dip(8),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    endLinearLayout = linearLayout {
                        orientation = LinearLayout.HORIZONTAL
                        backgroundResource = R.drawable.input_border
                        textView {
                            textResource = R.string.graduation
                            textColorResource = R.color.black20
                            textSize = 15f
                            gravity = Gravity.CENTER_VERTICAL
                        }.lparams(width = dip(110), height = matchParent){
                        }
                        endStudent = editText {
                            backgroundColorResource = R.color.whiteFF
                            singleLine = true
                            isFocusableInTouchMode = false
                            hintResource = R.string.graduationHint
                            hintTextColor = Color.parseColor("#B3B3B3")
                            rightPadding = dip(10)
                            textSize = 15f
                            onClick {
                                intermediary.twoOnClick("end")
                            }
                        }.lparams(width = matchParent, height = wrapContent){
                            weight = 1f
                        }
                        linearLayout {
                            gravity = Gravity.CENTER
                            imageView {
                                imageResource = R.mipmap.register_select_nor
                            }.lparams(width = matchParent,height = dip(15))
                        }.lparams(width = dip(8),height = matchParent)
                    }.lparams(width = matchParent,height = dip(44)){
                        topMargin = dip(20)
                    }

                    button {
                        backgroundResource = R.drawable.button_border
                        textResource = R.string.PtwoButton
                        textColorResource = R.color.whiteFF
                        textSize = 16f
                        onClick { submit() }
                    }.lparams(width = matchParent,height = dip(47)){
                        topMargin = dip(20)
                    }

                }

        }.view
    }

    fun showStartPicker(result:String) {
        startStudent.setText(result.trim())
    }

    interface Intermediary {

        fun addListFragment()

        fun twoOnClick(condition:String)
    }

    fun showEndPicker(result:String) {
        endStudent.setText(result.trim())
    }

    fun setEducation(education:String){
        educationEdit.setText(education)
    }

    // 点击跳转
    @SuppressLint("CheckResult")
    private fun submit(){
        thisDialog=DialogUtils.showLoading(context!!)
        mHandler.postDelayed(r, 12000)
        var school = tool.getEditText(schoolEdit)
        var startEducation = tool.getEditText(educationEdit)
        var endEducation = ""
        var major = tool.getEditText(majorEdit)
        var start = tool.getEditText(startStudent)
        var startDate = ""
        var end = tool.getEditText(endStudent)
        var endDate = ""

        if(school == ""){
            schoolLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else{
            schoolLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if(startEducation == ""){
            educationLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else{
            educationLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            when(startEducation){
                this.getString(R.string.educationOne) -> endEducation = "MIDDLE_SCHOOL"
                this.getString(R.string.educationTwo) -> endEducation = "HIGH_SCHOOL"
                this.getString(R.string.educationThree) -> endEducation = "SHORT_TERM_COLLEGE"
                this.getString(R.string.educationFour) -> endEducation = "BACHELOR"
                this.getString(R.string.educationFive) -> endEducation = "MASTER"
                this.getString(R.string.educationSix) -> endEducation = "DOCTOR"
            }
            education.educationalBackground = endEducation
        }

        if(major == ""){
            majorLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else{
            majorLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if(start == ""){
            startLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else{
            startLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            startDate = stringToLong(start,"yyyy-MM").toString()
        }

        if(end == ""){
            endLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }else{
            endLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            endDate = stringToLong(end,"yyyy-MM").toString()
        }

        if (start.isNotEmpty() && end.isNotEmpty() && stringToLong(end, "yyyy-MM") < stringToLong(start, "yyyy-MM")) {
            toast(this.getString(R.string.endOrStartTwo))
            startLinearLayout.backgroundResource = R.drawable.edit_text_empty
            endLinearLayout.backgroundResource = R.drawable.edit_text_empty
        }

        if (start.isNotEmpty() && end.isNotEmpty() && stringToLong(end, "yyyy-MM") > stringToLong(start, "yyyy-MM")) {
            startLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
            endLinearLayout.backgroundResource = R.drawable.edit_text_no_empty
        }

        if(school != "" && endEducation != "" && major != "" && startDate != "" && endDate != ""){

            education.schoolName = school
            education.attributes = myAttributes
            education.endDate = endDate
            education.major = major
            education.startDate = startDate


            var schoolId = ""

            var schoolRetrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.baseUrl))
            schoolRetrofitUils.create(RegisterApi::class.java)
                .getSchoolId(school,true)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe({
                    if(it.size() > 0){
                        println(it[0])
                        var js = it[0].asJsonObject
                        schoolId = js.get("id").toString()
                        education.schoolId = schoolId
                    }
                },{
                    println("该学校系统未录入")
                })

            val educationParams = mutableMapOf(
                "attributes" to education.attributes,
                "educationalBackground" to education.educationalBackground,
                "endDate" to education.endDate,
                "major" to education.major,
                "schoolName" to education.schoolName,
                "startDate" to education.startDate
            )

            val educationJson = JSON.toJSONString(educationParams)
            val educationBody = RequestBody.create(json, educationJson)


                    var resume = resumeId
                    var jobRetrofitUils = RetrofitUtils(mContext!!, this.getString(R.string.jobUrl))
                    jobRetrofitUils.create(RegisterApi::class.java)
                        .createEducation(educationBody, resume)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                        .subscribe({
                            if(it.code() in 200..299){
                                toast(this.getString(R.string.ptEducationSuccess))
                                var intent=Intent(activity,PersonInformationThreeActivity::class.java)
                                var bundle = Bundle()
                                bundle.putString("resumeId",resume)
                                intent.putExtra("bundle",bundle)
                                startActivity(intent)
                                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)
                                DialogUtils.hideLoading(thisDialog)
                            }else{
                                toast(this.getString(R.string.ptEducationFail))
                                DialogUtils.hideLoading(thisDialog)
                            }
                        },{
                            DialogUtils.hideLoading(thisDialog)
                        })

        }else{
            DialogUtils.hideLoading(thisDialog)
        }


    }

    // 类型转换
    private fun stringToLong(str: String, format: String): Long {
        val date = SimpleDateFormat(format).parse(str)
        return date.time
    }


    private fun closeKeyfocus(){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view!!.windowToken, 0)

        schoolEdit.clearFocus()
        majorEdit.clearFocus()
    }



}


