package com.example.sk_android.mvp.view.fragment.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import anet.channel.util.Utils
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.sk_android.R
import com.example.sk_android.mvp.model.register.Education
import com.example.sk_android.mvp.model.register.Person
import com.example.sk_android.utils.BaseTool
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.UI
import com.example.sk_android.mvp.view.activity.register.PersonInformationThreeActivity
import com.example.sk_android.utils.BasisTimesUtils
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

    companion object {
        fun newInstance(): PtwoMainBodyFragment {
            val fragment = PtwoMainBodyFragment()
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
                            onClick { showStartPicker() }
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
                            onClick { showEndPicker() }
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

    /**
     * 年月选择
     */
    private fun showStartPicker() {
        BasisTimesUtils.showDatePickerDialog(context, true, "请选择年月", 2015, 12, 22,
            object : BasisTimesUtils.OnDatePickerListener {

                override fun onConfirm(year: Int, month: Int, dayOfMonth: Int) {
                    startStudent.setText("$year-$month")
                }

                override fun onCancel() {
                    toast("cancle")
                }
            }).setDayGone()
    }

    interface Intermediary {

        fun addListFragment()
    }

    /**
     * 年月选择
     */
    private fun showEndPicker() {
        BasisTimesUtils.showDatePickerDialog(context, true, "请选择年月", 2015, 12, 22,
            object : BasisTimesUtils.OnDatePickerListener {

                override fun onConfirm(year: Int, month: Int, dayOfMonth: Int) {
                    endStudent.setText("$year-$month")
                }

                override fun onCancel() {
                    toast("cancle")
                }
            }).setDayGone()
    }

    fun setEducation(education:String){
        educationEdit.setText(education)
    }

    // 点击跳转
    @SuppressLint("CheckResult")
    private fun submit(){
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

        if(school != "" && endEducation != "" && major != "" && startDate != "" && endDate != ""){

            education.schoolName = school
            education.attributes = myAttributes
            education.endDate = endDate
            education.major = major
            education.startDate = startDate


            var schoolId = ""

            var schoolRetrofitUils = RetrofitUtils(mContext!!, "https://basic-info.sk.cgland.top/")
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

            println(education)

            var intent=Intent(activity,PersonInformationThreeActivity::class.java)
            var bundle = Bundle()
            bundle.putParcelable("education",education)
            intent.putExtra("bundle",bundle)
            startActivity(intent)
                activity!!.overridePendingTransition(R.anim.right_in, R.anim.left_out)

//            val educationJson = JSON.toJSONString(educationParams)
//            val educationBody = RequestBody.create(json,educationJson)

//            startActivity<PersonInformationThreeActivity>("education" to educationJson,"first" to person)
        }


    }

    // 类型转换
    private fun stringToLong(str: String, format: String): Long {
        val date = SimpleDateFormat(format).parse(str)
        return date.time
    }




}


